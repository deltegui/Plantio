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
    it('should listen in /users to post requests', async () => {
      await request(app)
          .post('/user/login')
          .set({name: 'manolo', password: 'man'})
          .expect(200);
    });
  });
});
