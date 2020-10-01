/* eslint-disable class-methods-use-this */
const Type = require('./type');

function booleanValidator(bool) {
  return typeof bool === 'boolean';
}

class BooleanType extends Type {
  constructor() {
    super();
    this.validators.push(booleanValidator);
  }
}

module.exports = BooleanType;
