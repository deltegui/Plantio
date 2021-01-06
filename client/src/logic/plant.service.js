import {
  emphasisPlant,
  plantTypes,
} from '../game';
import store from '../store';
import Plant from './plant';
import {
  InvalidPlantName,
  AlreadyExists,
  NotFound,
  UnknownPlantType,
  CannotRecollect,
  ReachedBagMaxOccupation,
} from './plant.errors';
import gameService from './game.service';
import userService from './user.service';
import api from '../api';

function getRandomInt(max) {
  return Math.floor(Math.random() * Math.floor(max)) + 1;
}

function getRandomAmountSeeds() {
  let seeds = getRandomInt(4);
  const finalBagSize = seeds + userService.sumElementsInBag();
  if (finalBagSize > userService.getBagMaxSize()) {
    seeds = userService.getBagMaxSize() - userService.sumElementsInBag();
  }
  if (seeds <= 0) {
    throw new ReachedBagMaxOccupation();
  }
  return seeds;
}

export default {
  createPlantForPosition(position) {
    const plantData = this.getForPosition(position);
    if (!plantData) {
      throw new NotFound(position);
    }
    return new Plant(plantData);
  },

  getForPosition({ x, y }) {
    const matched = store.save.filter(({ position }) => position.x === x && position.y === y);
    return matched.length === 0 ? false : matched[0];
  },

  nameExists(name) {
    const names = plantTypes.map(({ key }) => key).filter((key) => key === name);
    return names.length > 0;
  },

  emphasisForPosition(position) {
    if (!this.getForPosition(position)) {
      throw new NotFound(position);
    }
    emphasisPlant(position);
  },

  add(name, position) {
    if (!this.nameExists(name)) {
      throw new InvalidPlantName(name);
    }
    if (this.getForPosition(position)) {
      throw new AlreadyExists(position);
    }
    const plant = {
      plant: name,
      position,
      phase: 0,
      watered: 'dry',
      humidity: 50,
    };
    gameService.addPlant(plant);
  },

  waterForPosition(position) {
    const plant = this.createPlantForPosition(position);
    plant.water();
    gameService.updatePlant(plant);
  },

  dryForPosition(position) {
    const plant = this.createPlantForPosition(position);
    plant.dry();
    gameService.updatePlant(plant);
  },

  nextPhaseForPosition(position) {
    const plant = this.createPlantForPosition(position);
    plant.nextPhase();
    gameService.updatePlant(plant);
  },

  deleteForPosition(position) {
    gameService.deletePlant(position);
  },

  recollectPlant(position) {
    const plant = this.createPlantForPosition(position);
    if (plant.phase !== Plant.phaseLimit) {
      throw new CannotRecollect(plant.plant);
    }
    const seeds = getRandomAmountSeeds();
    for (let i = 0; i < seeds; i++) {
      userService.addToBag(plant.plant);
    }
    this.deleteForPosition(position);
    api.user.update(store.user.token, store.user);
    return seeds;
  },

  getAll() {
    return store.save;
  },

  getPlantTypeForName(name) {
    const matchPlants = plantTypes.filter(({ key }) => key === name);
    if (matchPlants.length <= 0) {
      throw new UnknownPlantType(name);
    }
    return matchPlants[0];
  },
};
