const Type = require('./type');
const error = require('./error');

const numError = (message) => error('number', message);

function positiveValidator() {
  return {
    error: (key) =>
      numError(`key "${key}" should be positive`),
    test: (number) => number >= 0,
  };
}

function negativeValidator() {
  return {
    error: (key) =>
      numError(`key "${key}" should be negative`),
    test: (number) => number < 0,
  };
}

function numberValidator() {
  return {
    error: (value) =>
      numError(`element "${value}" must be a number`),
    test: (number) => typeof number === 'number',
  };
}

class NumberType extends Type {
  constructor() {
    super();
    this.validators.push(numberValidator());
  }

  get positive() {
    this.validators.push(positiveValidator());
    return this;
  }

  get negative() {
    this.validators.push(negativeValidator());
    return this;
  }
}

module.exports = NumberType;
