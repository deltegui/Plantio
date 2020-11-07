const url = 'http://localhost:5000';

const isResponseError = (res) => res.Code && res.Reason && res.Fix;

function handleResponse(raw) {
  const res = raw.json();
  if (isResponseError(res)) {
    throw res;
  }
  return res;
}

function makeRequest({
  method,
  body,
  endpoint,
} = {
  method: 'GET',
  body: null,
}) {
  const config = {
    method,
    headers: {
      'Content-Type': 'application/json',
    },
  };
  if (body) {
    config.body = JSON.stringify(body);
  }
  return fetch(`${url}${endpoint}`, config)
    .then(handleResponse)
    .catch((err) => {
      console.error(err);
    });
}

export default {
  user: {
    login({ user, password }) {
      return makeRequest({
        method: 'POST',
        endpoint: '/user/login',
        body: {
          Name: user,
          Password: password,
        },
      });
    },

    register({ user, password }) {
      return makeRequest({
        method: 'POST',
        endpoint: '/user/register',
        body: {
          Name: user,
          Password: password,
        },
      });
    },
  },
};
