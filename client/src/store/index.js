import Vue from 'vue';

const store = Vue.observable({
  logged: false,
  user: {
    name: '',
    token: '',
  },
});

function login({ name, token }) {
  store.logged = true;
  store.user = {
    name,
    token: token.value,
  };
}

function logout() {
  store.logged = false;
}

Vue.prototype.$store = store;
Vue.prototype.$actions = {
  login,
  logout,
};
