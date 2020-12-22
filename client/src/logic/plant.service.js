import plantTypes from '../game/plant_types';
import {
  emphasisPlant,
} from '../game';
import store from '../store';
import {
  addPlantSave,
  updatePlantSave,
  deletePlantSave,
} from '../store/save';
import Plant from './plant';
import {
  InvalidPlantName,
  AlreadyExists,
  NotFound,
  UnknownPlantType,
} from './plant.errors';

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
    addPlantSave(plant);
  },

  waterForPosition(position) {
    const plantData = this.getForPosition(position);
    if (!plantData) {
      throw new NotFound(position);
    }
    const plant = new Plant(plantData);
    plant.water();
    updatePlantSave(plant);
  },

  dryForPosition(position) {
    const plantData = this.getForPosition(position);
    if (!plantData) {
      throw new NotFound(position);
    }
    const plant = new Plant(plantData);
    plant.dry();
    updatePlantSave(plant);
  },

  nextPhaseForPosition(position) {
    const plantData = this.getForPosition(position);
    if (!plantData) {
      throw new NotFound(position);
    }
    const plant = new Plant(plantData);
    plant.nextPhase();
    updatePlantSave(plant);
  },

  deleteForPosition(position) {
    deletePlantSave(position);
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
