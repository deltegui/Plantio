import store from './base';

export function login({ name, money, token }) {
  store.logged = true;
  store.user = {
    name,
    money,
    token: token.value,
  };
}

export function logout() {
  store.logged = false;
}
