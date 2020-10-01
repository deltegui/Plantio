/* eslint-disable class-methods-use-this */
const Type = require('./type');

function positiveValidator(number) {
  return number >= 0;
}

function negativeValidator(number) {
  return number < 0;
}

function numberValidator(number) {
  return typeof number === 'number';
}

class NumberType extends Type {
  constructor() {
    super();
    this.validators.push(numberValidator);
  }

  get positive() {
    this.validators.push(positiveValidator);
    return this;
  }

  get negative() {
    this.validators.push(negativeValidator);
    return this;
  }
}

module.exports = NumberType;
