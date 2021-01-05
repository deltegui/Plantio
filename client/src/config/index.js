/* eslint-disable no-param-reassign */

function cookieToObj() {
  const cookies = decodeURIComponent(document.cookie);
  const pairs = cookies.split('; ');
  return pairs
    .map((pair) => {
      const [key, value] = pair.split('=');
      return {
        key,
        value,
      };
    })
    .reduce((prev, current) => {
      prev[current.key] = current.value;
      return prev;
    }, {});
}

export default {
  set(key, value) {
    document.cookie = `${key}=${value}`;
  },

  get(key) {
    return cookieToObj()[key];
  },

  delete(key) {
    document.cookie = `${key}=; expires=Thu, 01 Jan 1970 00:00:00 UTC; path=/;`;
  },
};
