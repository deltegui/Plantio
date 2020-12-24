import api from '../api';
import store, {
  actions,
} from '../store';

function getLocation() {
  return new Promise((resolve, reject) => {
    if (navigator.geolocation) {
      navigator.geolocation.getCurrentPosition(resolve);
    } else {
      reject();
    }
  });
}

export default {
  refresh() {
    return getLocation()
      .then((position) => api.weather.read(store.user.token, position.coords))
      .then((weather) => actions.refreshWeather(weather))
      .catch(console.error);
  },
};
