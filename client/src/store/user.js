import store from './base';
import { startMoving } from '../game';

export function login({ name, token }) {
  store.logged = true;
  store.user = {
    name,
    token: token.value,
  };
}

export function logout() {
  store.logged = false;
  startMoving();
}
