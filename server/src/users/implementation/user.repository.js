const knex = require('../db');

const userKnex = knex('users');

module.exports = {
  /**
   * Creates a new user
   * @param {User} param0
   * @return {Promise<User>}
   */
  async create({name, password, lastConnection}) {
    return userKnex.insert({
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
    return userKnex
        .where({name})
        .then((rows) => rows.length === 1);
  },
};
