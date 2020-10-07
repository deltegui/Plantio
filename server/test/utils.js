const knex = require('../src/db');
const hasher = require('../src/users/implementation/hasher');

module.exports = {
  db: {
    async reset() {
      const tables = [
        'tokens',
        'users',
      ];
      await Promise.all(tables.map((t) => knex(t).del()));
    },

    async initialize() {
      await knex.migrate.latest();
    },
  },
  src: (path) => `../../src${path}`,
  async createUser() {
    return {
      name: 'elver',
      password: await hasher.hash('gadura'),
      clearPassword: 'gadura',
      lastConnection: new Date(1234),
    };
  },
};
