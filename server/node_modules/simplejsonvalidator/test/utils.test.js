/* eslint-disable no-unused-expressions */
const utils = require('../utils');
const expect = require('chai').expect;

describe('utils', () => {
  describe('isObjectLiteral', () => {
    it('should return true if object is literal', () => {
      expect(utils.isObjectLiteral({})).to.be.true;
    });

    it('should return false is object is created with new', () => {
      function MyObject() {}
      expect(utils.isObjectLiteral(new MyObject())).to.be.false;
    });
  });

  describe('flattenObject', () => {
    it('should create a one-level object from multi-level object', () => {
      const originalObject = {
        param1: 'param1',
        obj: {
          param2: 'param2',
          obj: {
            param3: 'param3',
          },
        },
      };
      const expectedObject = {
        param1: 'param1',
        param2: 'param2',
        param3: 'param3',
      };
      expect(utils.flattenObject(originalObject)).to.deep.equal(expectedObject);
    });
  });

  describe('arrayEquals', () => {
    it('arrayEquals should check if two arrays are equals', () => {
      const arr1 = [1, 2, 3, 4];
      const arr2 = [1, 2, 3, 4];
      expect(utils.arrayEquals(arr1, arr2)).to.be.true;
    });

    it('arrayEquals should check if two arrays are deep equals', () => {
      const arr1 = [1, 2, ['apple', 'linux'], 4];
      const arr2 = [1, 2, ['apple', 'linux'], 4];
      expect(utils.arrayEquals(arr1, arr2)).to.be.true;
    });

    it('arrayEquals should check if two arrays are not deep equals', () => {
      const arr1 = [1, 2, ['apple', 'linux'], 4];
      const arr2 = [1, 2, ['apple', 'windows'], 4];
      expect(utils.arrayEquals(arr1, arr2)).to.be.false;
    });
  });
});
