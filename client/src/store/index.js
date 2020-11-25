import Vue from 'vue';
import api from '../api';

const store = Vue.observable({
  logged: false,
  user: {
    name: '',
    token: '',
  },
  weather: {
    poblation: 'Unkown',
    temperature: 0,
    state: 'rainy',
    season: 'Unknown',
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

function getLocation() {
  return new Promise((resolve, reject) => {
    if (navigator.geolocation) {
      navigator.geolocation.getCurrentPosition(resolve);
    } else {
      reject();
    }
  });
}

function getWeatherInformation(token) {
  return getLocation()
    /* eslint-disable no-alert */
    .catch(() => alert('No puedes jugar si no tienes la localizacion activada'))
    .then((position) => api.weather.read(token, position.coords))
    .catch(console.error);
}

function refreshWeather() {
  return getWeatherInformation(store.user.token)
    .then((weather) => {
      if (!weather) return;
      store.weather.poblation = weather.location;
      store.weather.state = weather.state.toLowerCase();
      store.weather.temperature = weather.temperature;
    });
}

Vue.prototype.$store = store;
Vue.prototype.$actions = {
  login,
  logout,
  refreshWeather,
};
