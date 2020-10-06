const request = require('supertest');
const {src, db} = require('../utils');
const knex = require(src('/db'));
const app = require(src('/app'));

beforeAll(db.initialize);
afterAll(knex.destroy.bind(knex));
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
  });
});
