import Vue from 'vue';
import store from './base';
import refreshWeather from './weather';
import {
  login,
  logout,
} from './user';
import {
  addPlantSave,
  updatePlantSave,
  loadGame,
  deletePlantSave,
} from './save';

Vue.prototype.$store = store;
Vue.prototype.$actions = {
  login,
  logout,
  refreshWeather,
  addPlantSave,
  updatePlantSave,
  loadGame,
};

export default store;

export const actions = {
  addPlantSave,
  updatePlantSave,
  deletePlantSave,
  loadGame,
  login,
  logout,
  refreshWeather,
};
