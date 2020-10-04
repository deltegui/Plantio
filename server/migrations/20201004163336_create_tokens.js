module.exports = {
  up: async (knex) => {
    return knex.schema.createTable('tokens', (table) => {
      table.increments('id').primary();
      table.datetime('created').notNullable();
      table.string('user');

      table.foreign('user').references('name').inTable('users');
    });
  },

  down: async (knex) => {
    return knex.schema.dropTable('tokens');
  },
};
