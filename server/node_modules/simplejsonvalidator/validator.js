/* eslint-disable no-restricted-syntax */
const { isObjectLiteral, flattenObject } = require('./utils');

function notHaveRequiedKey(struct) {
  const flattenStruct = flattenObject(struct);
  let notRequired = true;
  Object.keys(flattenStruct).forEach((key) => {
    notRequired = notRequired && !flattenStruct[key].isRequired;
  });
  return notRequired;
}

class Validator {
  constructor(jsonStructure) {
    this.structure = jsonStructure;
  }

  validate(json, struct = this.structure) {
    if(!json) {
      return notHaveRequiedKey(struct);
    }
    for(const key of Object.keys(struct)) {
      if(isObjectLiteral(struct[key])) {
        if(!this.validate(json[key], struct[key])) return false;
      } else {
        const isPropertyPresent = Object.prototype.hasOwnProperty.call(json, key);
        if(!struct[key].isValid(json[key], isPropertyPresent)) return false;
      }
    }
    return true;
  }
}

module.exports = Validator;
