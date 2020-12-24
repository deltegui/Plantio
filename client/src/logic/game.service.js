import store, { actions } from '../store';
import {
  reload,
  stopMoving,
  CropSize,
  plantTypes,
  createGame,
} from '../game';
import api from '../api';

export default {
  get CropSize() {
    return CropSize;
  },

  get plantTypes() {
    return plantTypes;
  },

  updatePlant(plant) {
    actions.updatePlantSave(plant);
    reload(store.save);
  },

  addPlant(plant) {
    actions.addPlantSave(plant);
    reload(store.save);
  },

  deletePlant(position) {
    actions.deletePlantSave(position);
    reload(store.save);
  },

  async loadGame() {
    const game = await api.game.load(store.user.token);
    actions.loadGame(game);
    stopMoving();
    reload(store.save);
  },

  async saveGame() {
    return api.game.save(store.user.token, store.save);
  },

  createGame(canvas, size) {
    createGame(canvas, size);
  },
};
