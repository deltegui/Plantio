const knex = require('../src/db');

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
};
