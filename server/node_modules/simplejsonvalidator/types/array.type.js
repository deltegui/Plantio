const Type = require('./type');
const error = require('./error');

const arrError = (message) => error('array', message);

function exactLengthValidator(length) {
  return {
    error: (key) =>
      arrError(`key "${key}" must have exact length of ${length}`),
    test: (arr) => arr.length === length,
  };
}

function lengthUpperValidator(length) {
  return {
    error: (key) =>
      arrError(`key "${key}" must be upper of ${length}`),
    test: (arr) => arr.length > length,
  };
}

function lengthLowerValidator(length) {
  return {
    error: (key) =>
      arrError(`key "${key}" must be lower of ${length}`),
    test: (arr) => arr.length < length,
  };
}

function emptyValidator() {
  return {
    error: () => arrError(`array must not be empty`),
    test: (arr) => arr.length !== 0,
  };
}

function arrayValidator() {
  return {
    error: (value) =>
      arrError(`element "${value}" must be array`),
    test: (arr) => arr instanceof Array,
  };
}

class ArrayType extends Type {
  constructor() {
    super();
    this.validators.push(arrayValidator());
  }

  exactLength(length) {
    this.validators.push(exactLengthValidator(length));
    return this;
  }

  lengthLowerTo(length) {
    this.validators.push(lengthLowerValidator(length));
    return this;
  }

  lengthUpperTo(length) {
    this.validators.push(lengthUpperValidator(length));
    return this;
  }

  get notEmpty() {
    this.validators.push(emptyValidator());
    return this;
  }
}

module.exports = ArrayType;
