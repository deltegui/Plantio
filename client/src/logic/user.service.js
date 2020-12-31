import store, { actions } from '../store';
import {
  startMoving,
  stopMoving,
} from '../game';
import api from '../api';
import weatherService from './weather.service';
import gameService from './game.service';
import { MissingItemInBag } from './store.errors';

function handleLogin(res) {
  stopMoving();
  actions.login(res);
  weatherService.refresh();
  gameService.loadGame();
  return res;
}

export default {
  login({ user, password }) {
    return api.user.login({
      user,
      password,
    }).then(handleLogin);
  },

  logout() {
    actions.logout();
    startMoving();
  },

  register({ user, password }) {
    return api.user.register({
      user,
      password,
    }).then(handleLogin);
  },

  async addToBag(item) {
    actions.addToBag(item);
    return api.user.update(store.user.token, store.user);
  },

  async substractFrombag(item) {
    if (!this.findItemInBag(item)) {
      throw new MissingItemInBag(item);
    }
    actions.substractFromBag(item);
    return api.user.update(store.user.token, store.user);
  },

  findItemInBag(item) {
    return store.user.bag.find((element) => element.item === item);
  },

  getBag() {
    return store.user.bag;
  },
};
