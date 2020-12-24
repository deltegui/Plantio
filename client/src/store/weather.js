import store from './base';

export default async function refreshWeather(weather) {
  if (!weather) return;
  store.weather.poblation = weather.poblation;
  store.weather.state = weather.state;
  store.weather.temperature = weather.temperature;
  store.weather.sunrise = weather.sunrise;
  store.weather.sunset = weather.sunset;
}
