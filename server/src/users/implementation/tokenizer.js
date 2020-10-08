const fs = require('fs');
const jwt = require('jsonwebtoken');

const fileName = process.env.JWT_PRIVATE_FILE;
const privateKey = fs.readFileSync(fileName);

module.exports = {
  /**
   * @param {Object} payload
   * @return {String}
   */
  generateFor(payload) {
    return {
      value: jwt.sign(payload, privateKey),
      created: new Date(),
    };
  },
};
