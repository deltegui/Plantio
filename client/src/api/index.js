import makeRequest from './make_request';

export default {
  user: {
    login({ user, password }) {
      return makeRequest({
        method: 'POST',
        endpoint: '/user/login',
        body: {
          name: user,
          password,
        },
      });
    },

    register({ user, password }) {
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
    read(token, { latitude, longitude }) {
      console.log(`{${latitude}, ${longitude}}`);
      return makeRequest({
        method: 'GET',
        endpoint: `/weather/read?latitude=${latitude}&longitude=${longitude}`,
        token,
      });
    },
  },
};
