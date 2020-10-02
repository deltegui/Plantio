const validator = require('../index')();
const expect = require('chai').expect;

describe('Express MiddleWare', () => {
  let middleware;
  const res = {
    status() {
      return this;
    },
    send() {
      return this;
    },
  };

  before(() => {
    validator.create((t) => ({
      name: t.string.min(1).required,
      age: t.number.positive.required,
      data: {
        token: t.string.min(0),
      },
    }), 'exampleValidator');
    middleware = validator.createMiddleware('exampleValidator');
  });

  it('should be a function', () => {
    expect(middleware).to.be.a('function');
  });

  it('should call next if json is ok', () => {
    let called = false;
    const next = () => {
      called = true;
    };
    const req = {
      body: {
        name: 'demo',
        age: 22,
        data: {
          token: 'lalalalala',
        },
      },
    };
    middleware(req, res, next);
    expect(called).to.be.true;
  });

  it('should call send if json is bad', () => {
    let called = false;
    const req = {
      body: {
        name: 22,
        age: '22',
      },
    };
    const resMod = Object.assign(res, {
      send() {
        called = true;
        return this;
      },
    });
    middleware(req, resMod, () => {});
    expect(called).to.be.true;
  });

  it('should return error if json is bad', () => {
    const expectedJson = {
      status: 400,
      message: 'invalid json',
    };
    const req = {
      body: {
        name: 22,
        age: '22',
      },
    };
    const resMod = Object.assign(res, {
      send(j) {
        expect(j).to.deep.equal(expectedJson);
      },
    });
    middleware(req, resMod, () => {});
  });
});
