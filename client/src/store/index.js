import Vue from 'vue';
import store from './base';
import refreshWeather from './weather';
import { login, logout } from './user';

Vue.prototype.$store = store;
Vue.prototype.$actions = {
  login,
  logout,
  refreshWeather,
};
