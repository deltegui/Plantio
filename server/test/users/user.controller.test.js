const request = require('supertest');
const {
  src,
  db,
  createUser,
} = require('../utils');
const knex = require(src('/db'));
const app = require(src('/app'));

beforeAll(db.initialize);
afterAll(async () => await knex.destroy());
beforeEach(db.reset);

describe('UserController', () => {
  describe('register endpoint', () => {
    it('should return logged user', async () => {
      await request(app)
          .post('/user')
          .send({name: 'manolo', password: 'man'})
          .expect(200)
          .then((res) => {
            const user = res.body;
            expect(user.name).toBe('manolo');
            expect(user.password).toBeFalsy;
            expect(user.lastConnection).toBeTruthly;
            expect(user.jwt.length).toBe(284);
          });
    });

    it('should save or replace a token', async () => {
      await request(app)
          .post('/user')
          .send({name: 'manolo', password: 'man'})
          .expect(200)
          .then(async (res) => {
            const token = res.body.jwt;
            const name = res.body.name;
            const storedToken = await knex('tokens')
                .where({user: name})
                .first();
            expect(token).toBe(storedToken.value);
          });
    });

    it('should return error if user already exists', async () => {
      const {
        name,
        password,
        clearPassword,
        lastConnection,
      } = await createUser();
      await knex('users').insert({
        name,
        password,
        lastConnection,
      });
      await request(app)
          .post('/user')
          .send({name, password: clearPassword})
          .expect(200)
          .then(async (res) => {
            expect(res.body).toMatchObject({
              code: 100,
              message: 'User already exists',
            });
          });
    });
  });

  describe('login controller', () => {
    it('should login a existing user', async () => {
      const expectedUser = await createUser();
      await knex('users').insert({
        name: expectedUser.name,
        password: expectedUser.password,
        lastConnection: expectedUser.lastConnection,
      });
      await request(app)
          .post('/user/login')
          .send({name: expectedUser.name, password: expectedUser.clearPassword})
          .expect(200)
          .then(async (res) => {
            expect(res.body.name).toBe(expectedUser.name);
          });
    });

    it('should store the user', async () => {
      const expectedUser = await createUser();
      await knex('users').insert({
        name: expectedUser.name,
        password: expectedUser.password,
        lastConnection: expectedUser.lastConnection,
      });
      await request(app)
          .post('/user/login')
          .send({name: expectedUser.name, password: expectedUser.clearPassword})
          .expect(200)
          .then(async (res) => {
            const name = res.body.name;
            const storedUser = await knex('users')
                .where({name})
                .first();
            expect(storedUser).toMatchObject({
              name: expectedUser.name,
              password: expectedUser.password,
            });
          });
    });

    it('should store a new token', async () => {
      const expectedUser = await createUser();
      await knex('users').insert({
        name: expectedUser.name,
        password: expectedUser.password,
        lastConnection: expectedUser.lastConnection,
      });
      await request(app)
          .post('/user/login')
          .send({name: expectedUser.name, password: expectedUser.clearPassword})
          .expect(200)
          .then(async (res) => {
            const token = res.body.jwt;
            const name = res.body.name;
            const storedToken = await knex('tokens')
                .where({user: name})
                .first();
            expect(token).toBe(storedToken.value);
          });
    });
  });
});
