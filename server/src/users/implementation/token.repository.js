const knex = require('../../db');

module.exports = {
  /**
   * Saves a token for given user
   * @param {User} user
   * @param {{token: string, created: date}} token
   * @return {Promise}
   */
  async save(user, {value, created}) {
    const data = {
      created,
      value,
      user: user.name,
    };
    return knex('tokens').insert(data).then(() => data);
  },
};
