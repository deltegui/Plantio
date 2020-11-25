const url = 'localhost:3000';

function timeout(promise, limit) {
  return new Promise((resolve, reject) => {
    const error = {
      message: 'Server does not respond',
      code: 500,
    };
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

const isResponseError = (res) => !!res.message && !!res.code;

async function handleResponse(raw) {
  const res = await raw.json();
  if (isResponseError(res)) {
    throw res;
  }
  return res;
}

export default function makeRequest({
  method,
  body,
  endpoint,
  token,
} = {
  method: 'GET',
  body: null,
}) {
  const config = {
    method,
    credentials: 'include',
    withCredentials: true,
    headers: {
      'Content-Type': 'application/json',
    },
  };
  if (body) {
    config.body = JSON.stringify(body);
  }
  if (token) {
    config.headers.Authorization = `Bearer ${token}`;
  }
  return timeout(fetch(`http://${url}${endpoint}`, config), 1000)
    .then(handleResponse);
}
