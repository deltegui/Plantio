const knex = require('../../db');

module.exports = {
  /**
   * Creates a new user
   * @param {User} param0
   * @return {Promise<User>}
   */
  async create({name, password, lastConnection}) {
    return knex('users').insert({
      name,
      password,
      lastConnection,
    }).then(() => ({
      name,
      password,
      lastConnection,
    }));
  },

  /**
   * Check if a user exist with name;
   * @param {String} name
   * @return {Promise<Number>}
   */
  async existsWithName(name) {
    return this.getByName(name)
        .then((user) => !!user);
  },

  /**
   * Returns the user identified by
   * name. If no user is found returns null.
   * Check if user exists with existsWithName method
   * before calling this.
   * @param {String} name
   * @return {Promise<User|null>}
   */
  async getByName(name) {
    return knex('users')
        .where({name})
        .first();
  },

  /**
   * Updates a user.
   * @param {User} user
   */
  async update({password, lastConnection}) {
    await knex('users').update({password, lastConnection});
  },
};
