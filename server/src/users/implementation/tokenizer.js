const fs = require('fs');
const jwt = require('jsonwebtoken');

const fileName = process.env.JWT_PRIVATE_FILE;
const privateKey = fs.readFileSync(fileName);

module.exports = {
  /**
   * Generates a token for a payload
   * @param {Object} payload
   * @return {{value: String, created: Date}}
   */
  generateFor(payload) {
    return {
      value: jwt.sign(payload, privateKey),
      created: new Date(),
    };
  },

  /**
   * @param {String} token
   * @return {String}
   */
  getPayload(token) {
    return jwt.decode(token);
  },
};
