const request = require('supertest');
const {src} = require('../utils');
const app = require(src('/app'));
const db = require(src('/db'));

describe('UserController', () => {
  beforeEach(async () => {
    await db.migrate.rollback();
    await db.migrate.latest();
  });

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
            const storedTokens = await db('tokens').where({user: name});
            expect(token).toBe(storedTokens[0].value);
          });
    });
  });
});
