import makeRequest from './make_request';

function gameToServer(plants) {
  return plants.map(({
    plant,
    phase,
    position,
    watered,
    humidity,
  }) => ({
    type: plant.toUpperCase(),
    phase,
    position,
    watered: watered.toUpperCase(),
    humidity,
  }));
}

function gameToSystem({ crop }) {
  return crop.map(({
    type,
    phase,
    position,
    watered,
    humidity,
  }) => ({
    plant: type.toLowerCase(),
    phase,
    position,
    watered: watered.toLowerCase(),
    humidity,
  }));
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

export default {
  user: {
    async login({ user, password }) {
      return makeRequest({
        method: 'POST',
        endpoint: '/user/login',
        body: {
          name: user,
          password,
        },
      });
    },

    async register({ user, password }) {
      return makeRequest({
        method: 'POST',
        endpoint: '/user/register',
        body: {
          name: user,
          password,
        },
      });
    },
  },

  weather: {
    async read(token, { latitude, longitude }) {
      return makeRequest({
        method: 'GET',
        endpoint: `/weather/read?latitude=${latitude}&longitude=${longitude}`,
        token,
      }).then(({
        location,
        state,
        temperature,
        sunrise,
        sunset,
      }) => ({
        poblation: location,
        state: toWeatherImage(state),
        temperature,
        sunrise,
        sunset,
      }));
    },
  },

  game: {
    async save(token, game) {
      return makeRequest({
        method: 'POST',
        endpoint: '/game',
        token,
        body: gameToServer(game),
      });
    },

    async load(token) {
      return makeRequest({
        method: 'GET',
        endpoint: '/game',
        token,
      }).then(gameToSystem);
    },
  },
};
