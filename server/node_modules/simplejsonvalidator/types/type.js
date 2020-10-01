/* eslint-disable no-restricted-syntax */
const { arrayEquals } = require('../utils');

function shouldBeValidator(admitValues) {
  return (value) => {
    if(value instanceof Array) {
      const arraysToCompare = admitValues.filter(e => e instanceof Array);
      return arraysToCompare.find(e => arrayEquals(value, e));
    }
    return admitValues.find(element => element === value);
  };
}

class Type {
  constructor() {
    this.validators = [];
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
    if(!present && !this.isRequired) return true;
    for(const validator of this.validators) {
      if(!validator(value)) return false;
    }
    return true;
  }
}

module.exports = Type;
