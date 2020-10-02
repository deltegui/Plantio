const {arrayEquals} = require('../utils');
const error = require('./error');

function shouldBeValidator(admitValues) {
  const test = (value) => {
    if (value instanceof Array) {
      const arraysToCompare = admitValues.filter((e) => e instanceof Array);
      return arraysToCompare.find((e) => arrayEquals(value, e));
    }
    return admitValues.find((element) => element === value);
  };
  return {
    error: (value) =>
      error('generic', `element ${value} should be one of [${admitValues}]`),
    test,
  };
}

class Type {
  constructor() {
    this.validators = [];
    this.errors = [];
  }

  shouldBe(...values) {
    this.validators.push(shouldBeValidator(values));
    return this;
  }

  get required() {
    this.isRequired = true;
    return this;
  }

  isValid(value, present = true) {
    this.errors = [];
    if (!present && !this.isRequired) {
      return true;
    }
    for (const validator of this.validators) {
      if (!validator.test(value)) {
        this.errors.push(validator.error);
        return false;
      }
    }
    return true;
  }
}

module.exports = Type;
