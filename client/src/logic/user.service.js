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
    const itemToRemove = this.findItemInBag(item);
    console.log(itemToRemove);
    if (!itemToRemove) {
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

  getBagMaxSize() {
    return store.user.bagSize;
  },

  sumElementsInBag() {
    return store.user.bag.reduce((sum, current) => current.amount + sum, 0);
  },

  getBagOccupation() {
    const currentElements = this.sumElementsInBag();
    return Math.round((currentElements / store.user.bagSize) * 100);
  },
};
