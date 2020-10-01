const types = require('./types');
const Validator = require('./validator');
const expressMiddleware = require('./express-middleware');

module.exports = () => ({
  validators: {},

  /**
   * Creates and stores a new json validator with
   * a name.
   * @param {function} callback
   * @param {string} name
   * @return {Validator}
   */
  create(callback, name) {
    const schema = callback(types);
    const validator = new Validator(schema);
    this.validators[name] = validator;
    return validator;
  },

  /**
   * Check if json is valid according to validator
   * associated with the validatorName.
   * @param {Object} json
   * @param {string} validatorName
   * @return {boolean}
   */
  validate(json, validatorName) {
    return this.validators[validatorName].validate(json);
  },

  /**
   * Creates a new express middleware that
   * checks if the json validates with the
   * given validator.
   * @param {string} validatorName
   * @return {function}
   */
  createMiddleware(validatorName) {
    return expressMiddleware(this.validators[validatorName]);
  },
});
