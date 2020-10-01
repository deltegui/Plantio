/* eslint-disable no-unused-expressions */
const expect = require('chai').expect;
const types = require('../types');

describe('Types', () => {
  it('when a falsy value is passed when is not the type, should return false', () => {
    expect(types.string.isValid(0)).to.be.false;
    expect(types.array.isValid('')).to.be.false;
    expect(types.boolean.isValid(null)).to.be.false;
    expect(types.array.isValid(NaN)).to.be.false;
    expect(types.array.isValid(undefined)).to.be.false;
  });

  describe('StringType', () => {
    it('should validate a string', () => {
      expect(types.string.isValid('string')).to.be.true;
      expect(types.string.isValid(11)).to.be.false;
    });

    it('should check if string is under max limit', () => {
      expect(types.string.max(11).isValid('hi!')).to.be.true;
      expect(types.string.max(2).isValid('hello')).to.be.false;
    });

    it('should check if string is upper min limit', () => {
      expect(types.string.min(1).isValid('hi!')).to.be.true;
      expect(types.string.min(11).isValid('hello')).to.be.false;
    });

    it('should test a regular expression on string', () => {
      expect(types.string.matches(/^A/).isValid('Algete')).to.be.true;
      expect(types.string.matches(/z$/).isValid('Algete')).to.be.false;
    });
  });

  describe('NumberType', () => {
    it('should validate a number', () => {
      expect(types.number.isValid(11)).to.be.true;
      expect(types.number.isValid('hi')).to.be.false;
    });

    it('should check if a number is positive', () => {
      expect(types.number.positive.isValid(1)).to.be.true;
      expect(types.number.positive.isValid(-1)).to.be.false;
    });

    it('should check if a number is negative', () => {
      expect(types.number.negative.isValid(-1)).to.be.true;
      expect(types.number.negative.isValid(1)).to.be.false;
    });

    it('when NaN is passed to number type, should be true', () => {
      expect(types.number.isValid(NaN)).to.be.true; // TODO Should this be false???
    });
  });

  describe('BooleanType', () => {
    it('should validate a boolean', () => {
      expect(types.boolean.isValid(true)).to.be.true;
      expect(types.boolean.isValid(false)).to.be.true;
    });

    it('should not validate something that is not boolean', () => {
      expect(types.boolean.isValid('blabla')).to.be.false;
    });
  });

  describe('ArrayType', () => {
    it('should validate an array', () => {
      expect(types.array.isValid([])).to.be.true;
      expect(types.array.isValid({})).to.be.false;
    });

    it('should check if an array have exact length', () => {
      expect(types.array.exactLength(3).isValid([0, 1, 2])).to.be.true;
      expect(types.array.exactLength(1).isValid([1, 2])).to.be.false;
    });

    it('should check if an array have upper length', () => {
      expect(types.array.lengthUpperTo(1).isValid([0, 1, 2])).to.be.true;
      expect(types.array.lengthUpperTo(4).isValid([1, 2])).to.be.false;
    });

    it('should check if an array have lower length', () => {
      expect(types.array.lengthLowerTo(3).isValid([0, 1])).to.be.true;
      expect(types.array.lengthLowerTo(1).isValid([1, 2])).to.be.false;
    });

    it('should check if an array is not empty', () => {
      expect(types.array.notEmpty.isValid([0, 1])).to.be.true;
      expect(types.array.notEmpty.isValid([])).to.be.false;
    });
  });

  describe('DateType', () => {
    const date = new Date();
    it('should validate a date', () => {
      expect(types.date.isValid(date.toJSON())).to.be.true;
      expect(types.boolean.isValid('')).to.be.false;
    });

    it('should validate a date that is after other', () => {
      expect(types.date.afterDate(new Date(1997, 8)).isValid(new Date())).to.be.true;
      expect(types.date.afterDate(new Date(1997, 8)).isValid(new Date(1970, 0))).to.be.false;
    });

    it('should validate a date that is before other', () => {
      expect(types.date.beforeDate(new Date(1997, 8)).isValid(new Date())).to.be.false;
      expect(types.date.beforeDate(new Date(1997, 8)).isValid(new Date(1970, 0))).to.be.true;
    });
  });
});
