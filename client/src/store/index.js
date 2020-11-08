import Vue from 'vue';

const store = Vue.observable({
  logged: false,
  user: {
    name: '',
    token: '',
  },
});

function login({ user, token }) {
  store.logged = true;
  store.user = {
    name: user,
    token,
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
