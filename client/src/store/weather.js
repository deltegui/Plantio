import store from './base';
import api from '../api';

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
    .then((position) => api.weather.read(token, position.coords))
    .catch(console.error);
}

function toWeatherImage(weatherState) {
  const images = {
    RAIN: 'rainy',
    CLEAR: 'sunny',
    CLOUDS: 'cloudy',
    SNOW: 'rainy',
  };
  return images[weatherState];
}

export default async function refreshWeather() {
  return getWeatherInformation(store.user.token)
    .then((weather) => {
      if (!weather) return;
      store.weather.poblation = weather.location;
      store.weather.state = toWeatherImage(weather.state);
      store.weather.temperature = weather.temperature;
    });
}
