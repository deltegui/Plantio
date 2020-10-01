const Type = require('./type');

function beforeDateValidator(beforeDate) {
  return (dateJSON) => {
    const date = new Date(dateJSON);
    if(isNaN(date)) {
      return false;
    }
    return date.getTime() < beforeDate.getTime();
  };
}

function afterDateValidator(afterDate) {
  return (dateJSON) => {
    const date = new Date(dateJSON);
    if(isNaN(date)) {
      return false;
    }
    return date.getTime() > afterDate.getTime();
  };
}

function dateValidator(dateJSON) {
  return !isNaN(new Date(dateJSON));
}

class DateType extends Type {
  constructor() {
    super();
    this.validators.push(dateValidator);
  }

  beforeDate(date) {
    this.validators.push(beforeDateValidator(date));
    return this;
  }

  afterDate(date) {
    this.validators.push(afterDateValidator(date));
    return this;
  }
}

module.exports = DateType;
