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
} from './plant.errors';
import gameService from './game.service';

export default {
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
    const plantData = this.getForPosition(position);
    if (!plantData) {
      throw new NotFound(position);
    }
    const plant = new Plant(plantData);
    plant.water();
    gameService.updatePlant(plant);
  },

  dryForPosition(position) {
    const plantData = this.getForPosition(position);
    if (!plantData) {
      throw new NotFound(position);
    }
    const plant = new Plant(plantData);
    plant.dry();
    gameService.updatePlant(plant);
  },

  nextPhaseForPosition(position) {
    const plantData = this.getForPosition(position);
    if (!plantData) {
      throw new NotFound(position);
    }
    const plant = new Plant(plantData);
    plant.nextPhase();
    gameService.updatePlant(plant);
  },

  deleteForPosition(position) {
    gameService.deletePlant(position);
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
