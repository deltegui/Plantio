import store from '../store';
import api from '../api';
import {
  MissingItemInBag,
} from './store.errors';
import ApiError from './api.errors';

function findItemInBag(item) {
  return store.user.bag.find((element) => element.item === item);
}

export default {
  async buy({ item, amount }) {
    return api.store.buy(store.user.token, { item, amount })
      .then((result) => {
        console.log(result);
        return result;
      })
      .catch((error) => {
        throw new ApiError(error);
      });
  },

  async sell({ item, amount }) {
    const bagItem = findItemInBag(item);
    if (!bagItem) {
      throw new MissingItemInBag(item);
    }
    return api.store.sell(store.user.token, { item, amount })
      .then((result) => {
        console.log(result);
        return result;
      })
      .catch((error) => {
        throw new ApiError(error);
      });
  },
};
