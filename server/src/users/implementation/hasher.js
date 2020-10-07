const bcrypt = require('bcrypt');

module.exports = {
  /**
   * @param {String} password plain passwrod to hash
   * @return {Promise<String>} hashed password
   */
  async hash(password) {
    const saltRounds = 10;
    return bcrypt.hash(password, saltRounds);
  },

  /**
   * @param {String} password plain password to check
   * @param {String} hashed password
   * @return {Promise<boolean>}
   */
  async check(password, hashed) {
    return bcrypt.compare(password, hashed);
  },
};
