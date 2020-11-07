const url = 'localhost:8080';

function timeout(promise, limit) {
  return new Promise((resolve, reject) => {
    const error = { Reason: 'Server does not respond' };
    const interval = setInterval(() => reject(error), limit);
    promise
      .then((res) => {
        clearInterval(interval);
        resolve(res);
      })
      .catch((res) => {
        clearInterval(interval);
        reject(res);
      });
  });
}

const isResponseError = (res) => !!res.Reason;

async function handleResponse(raw) {
  const res = await raw.json();
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
  return timeout(fetch(`http://${url}${endpoint}`, config), 1000)
    .then(handleResponse);
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
