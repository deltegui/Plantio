/* eslint-disable max-classes-per-file */

export class MissingItemInBag {
  constructor(item) {
    this.msg = `Item ${item} is missing in bag`;
  }
}

export class NotEnoughMoney {
  constructor(currentMoney, price) {
    this.msg = `You cant afford a item with price ${price} because you have ${currentMoney}`;
  }
}
