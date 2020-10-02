const {isObjectLiteral, flattenObject} = require('./utils');
const error = require('./types/error');

function notHaveRequiedKey(struct, prevKeys) {
  const flattenStruct = flattenObject(struct);
  const errors = [];
  Object.keys(flattenStruct).forEach((key) => {
    if (flattenStruct[key].isRequired) {
      const keyStr = [...prevKeys, key].join('.');
      errors.push(error('required', `key ${keyStr} is required`));
    }
  });
  return errors;
}

function getErrorsIfKeyInvalid(json, struct, key, prevKeys) {
  const isPropertyPresent = Object.prototype.hasOwnProperty.call(json, key);
  const isInvalid = !struct[key].isValid(json[key], isPropertyPresent);
  const keyStr = [...prevKeys, key].join('.');
  return isInvalid ? struct[key].errors.map((e) => e(keyStr)) : [];
}

function validateLevel(json, struct, prevKeys = []) {
  if (!json) {
    return notHaveRequiedKey(struct, prevKeys);
  }
  let errors = [];
  for (const key of Object.keys(struct)) {
    if (isObjectLiteral(struct[key])) {
      const currentKeys = [...prevKeys, key];
      const innerErrors = validateLevel(json[key], struct[key], currentKeys);
      errors = [...errors, ...innerErrors];
    } else {
      const keyErrors = getErrorsIfKeyInvalid(json, struct, key, prevKeys);
      errors = [...errors, ...keyErrors];
    }
  }
  return errors;
}

class Validator {
  /**
   * Creates a vaildator from a json
   * valiation structure
   * @param {Object} jsonStructure
   */
  constructor(jsonStructure) {
    this.structure = jsonStructure;
    this._errors = [];
  }

  /**
   * Check if json is valid according
   * @param {Object} json
   * @return {boolean}
   */
  validate(json) {
    this._errors = validateLevel(json, this.structure);
    return this._errors.length === 0;
  }

  /**
   * Gets errors generated after validation. You
   * need to call validate before. Otherwise
   * will return empty array (no errors)
   * @return {[{ type: String, message: String }]} errors
   */
  get errors() {
    return this._errors;
  }
}

module.exports = Validator;
