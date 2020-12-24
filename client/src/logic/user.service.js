import { actions } from '../store';
import { startMoving } from '../game';
import api from '../api';
import weatherService from './weather.service';
import gameService from './game.service';

function handleLogin(res) {
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
};
