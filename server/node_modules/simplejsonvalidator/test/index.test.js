const jsonValidator = require('../index');
const expect = require('chai').expect;

let validator;

describe('JsonValidator', () => {
  beforeEach(() => {
    validator = jsonValidator();
  });

  it('for each call, should create differents objects', () => {
    const v1 = jsonValidator();
    const v2 = jsonValidator();
    expect(v1 === v2).to.be.false;
  });

  describe('create function', () => {
    it('should store a new validator', () => {
      validator.create((t) => ({
        user: t.string,
        age: t.number,
        text: t.string,
      }), 'demoValidator');
      const keys = Object.keys(validator.validators.demoValidator.structure);
      expect(keys).to.deep.equal(['user', 'age', 'text']);
    });
  });

  describe('validate function', () => {
    it('should generate errors for keys if are invalid', () => {
      const myValidator = validator.create((t) => ({
        key: t.string.required.min(5),
        user: {
          name: t.string.min(10),
          age: t.number.positive,
          connection: t.date.required,
          text: t.string,
        },
      }));
      const json = {
        user: {
          name: 'demo',
          age: -123,
          text: '123456789',
        },
      };
      const result = myValidator.validate(json);
      expect(result).to.be.false;
      expect(myValidator.errors).to.be.eql([
        {
          message: 'element "key" must be string',
          type: 'string',
        },
        {
          message: 'key "user.name" should be upper than 10',
          type: 'string',
        },
        {
          message: 'key "user.age" should be positive',
          type: 'number',
        },
        {
          message: 'element "user.connection" must be date',
          type: 'date',
        },
      ]);
    });

    it('should validate a json according to a validator', () => {
      const json = {
        user: 'demo',
        age: 123,
        text: '123456789',
      };
      validator.create((t) => ({
        user: t.string,
        age: t.number,
        text: t.string,
      }), 'demoValidator');
      const result = validator.validate(json, 'demoValidator');
      expect(result).to.be.true;
    });

    it('if a param is required, should appear in json', () => {
      const json = {
        user: 'demo',
        age: 123,
      };
      validator.create((t) => ({
        user: t.string,
        age: t.number,
        text: t.string.required,
      }), 'demoValidator');
      const result = validator.validate(json, 'demoValidator');
      expect(result).to.be.false;
    });

    it('should check if a param is one of the wanted values (bad)', () => {
      const json = {
        user: 'demo',
        age: 123,
        type: 'bad type',
      };
      validator.create((t) => ({
        user: t.string,
        age: t.number,
        type: t.string.shouldBe('reader', 'editor', 'admin').required,
      }), 'demoValidator');
      const result = validator.validate(json, 'demoValidator');
      expect(result).to.be.false;
    });

    it('should check if a param is one of the wanted values', () => {
      const json = {
        user: 'demo',
        age: 123,
        type: 'reader',
      };
      validator.create((t) => ({
        user: t.string,
        age: t.number,
        type: t.string.shouldBe('reader', 'editor', 'admin').required,
      }), 'demoValidator');
      const result = validator.validate(json, 'demoValidator');
      expect(result).to.be.true;
    });

    it('should check if a array deep is one of the wanted values', () => {
      const json = {
        user: 'demo',
        age: 123,
        type: [1, 'hola'],
      };
      validator.create((t) => ({
        user: t.string,
        age: t.number,
        type: t.array.shouldBe([1, 'hola'], ['hi']).required,
      }), 'demoValidator');
      const result = validator.validate(json, 'demoValidator');
      expect(result).to.be.true;
    });

    it('should check if a boolean is one of the wanted values', () => {
      const json = {
        user: 'demo',
        age: 123,
        type: false,
      };
      validator.create((t) => ({
        user: t.string,
        age: t.number,
        type: t.boolean.shouldBe(true).required,
      }), 'demoValidator');
      const result = validator.validate(json, 'demoValidator');
      expect(result).to.be.false;
    });

    it('should validate nested jsons if bad', () => {
      const json = {
        data: {
          name: 222,
          age: 123,
        },
      };
      validator.create((t) => ({
        data: {
          user: t.string,
          age: t.number,
          text: t.string.required,
        },
      }), 'demoValidator');
      const result = validator.validate(json, 'demoValidator');
      expect(result).to.be.false;
    });

    it('should validate nested jsons if good', () => {
      const json = {
        data: {
          name: 'demo',
          age: 123,
          text: 'hi!',
        },
      };
      validator.create((t) => ({
        data: {
          user: t.string,
          age: t.number,
          text: t.string.required,
        },
      }), 'demoValidator');
      const result = validator.validate(json, 'demoValidator');
      expect(result).to.be.true;
    });

    it('should validate more nested jsons', () => {
      const json = {
        data: {
          name: 'demo',
          age: 123,
          text: 'hi!',
          type: {
            name: 'bearer',
          },
        },
      };
      validator.create((t) => ({
        data: {
          user: t.string,
          age: t.number,
          text: t.string.required,
          type: {
            name: t.string,
          },
        },
      }), 'demoValidator');
      const result = validator.validate(json, 'demoValidator');
      expect(result).to.be.true;
    });

    it('should validate nested json if not present and itsn required', () => {
      const json = {
        data: {
          name: 'demo',
          age: 123,
          text: 'hi!',
        },
      };
      validator.create((t) => ({
        data: {
          user: t.string,
          age: t.number,
          text: t.string.required,
          type: {
            name: t.string,
          },
        },
      }), 'demoValidator');
      const result = validator.validate(json, 'demoValidator');
      expect(result).to.be.true;
    });
  });
});
