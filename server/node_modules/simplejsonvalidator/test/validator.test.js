/* eslint-disable no-unused-expressions */
const jsonValidator = require('../index')();
const expect = require('chai').expect;

describe('Validator', () => {
  const structure = jsonValidator.create((t) => ({
    name: t.string.required,
    age: t.number.required,
    email: t.string,
  }), '');

  it('should validate json types', () => {
    expect(structure.validate({
      name: 'demo',
      age: '22',
    })).to.be.false;
    expect(structure.validate({
      name: 'demo',
      age: 22,
    })).to.be.true;
  });

  it('should not validate undefined', () => {
    expect(structure.validate(undefined)).to.be.false;
  });
});
