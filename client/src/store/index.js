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
  loadEvents,
  deletePlantSave,
} from './save';

export const actions = {
  addPlantSave,
  updatePlantSave,
  deletePlantSave,
  loadGame,
  loadEvents,
  login,
  logout,
  refreshWeather,
};

Vue.prototype.$store = store;
Vue.prototype.$actions = actions;

export default store;
