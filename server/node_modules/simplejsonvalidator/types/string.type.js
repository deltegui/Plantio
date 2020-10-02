const Type = require('./type');
const error = require('./error');

const strError = (message) => error('string', message);

function maxStringValidator(maxLength) {
  return {
    error: (key) =>
      strError(`key "${key}" should be lower than ${maxLength}`),
    test: (str) => str.length < maxLength,
  };
}

function minStringValidator(minLength) {
  return {
    error: (key) =>
      strError(`key "${key}" should be upper than ${minLength}`),
    test: (str) => str.length > minLength,
  };
}

function stringValidator() {
  return {
    error: (key) =>
      strError(`element "${key}" must be string`),
    test: (str) => typeof str === 'string',
  };
}

function matchValidator(regex) {
  return {
    error: (key) =>
      strError(`key "${key}" should match ${regex}`),
    test: (str) => regex.test(str),
  };
}

class StringType extends Type {
  constructor() {
    super();
    this.validators.push(stringValidator());
  }

  min(minLength) {
    this.validators.push(minStringValidator(minLength));
    return this;
  }

  max(maxLength) {
    this.validators.push(maxStringValidator(maxLength));
    return this;
  }

  get notEmpty() {
    this.validators.push(minStringValidator(0));
    return this;
  }

  matches(regex) {
    this.validators.push(matchValidator(regex));
    return this;
  }
}

module.exports = StringType;
