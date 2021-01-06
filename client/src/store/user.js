import store from './base';

export function login({
  name,
  money,
  bag,
  bagSize,
  token,
}) {
  store.logged = true;
  store.user = {
    name,
    money,
    bag,
    bagSize,
    token: token.value,
  };
}

export function logout() {
  store.logged = false;
}

export function addToBag(item) {
  const elements = store.user.bag.filter((element) => element.item === item);
  if (elements.length >= 1) {
    elements[0].amount++;
    return;
  }
  store.user.bag.push({
    item,
    amount: 1,
  });
}

export function substractFromBag(item) {
  const elements = store.user.bag.filter((element) => element.item === item);
  if (elements <= 0) {
    return;
  }
  const foundElement = elements[0];
  if (foundElement.amount >= 2) {
    foundElement.amount--;
    return;
  }
  store.user.bag = store.user.bag.filter((element) => element.item !== item);
}
