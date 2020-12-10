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
