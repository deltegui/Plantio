module.exports = {
  up: async (knex) => {
    return knex.schema.createTable('users', (table) => {
      table.string('name').primary();
      table.string('password').notNullable();
      table.datetime('lastConnection').notNullable();
    });
  },

  down: async (knex) => {
    return knex.schema.dropTable('users');
  },
};
