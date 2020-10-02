const Type = require('./type');
const error = require('./error');

const dateError = (message) => error('date', message);

function formatDate(date) {
  return `${date.getFullYear()}-${date.getMonth() + 1}-${date.getDate()}` +
    ` ${date.getHours()}:${date.getMonth()}:${date.getSeconds()}`;
}

function beforeDateValidator(date) {
  const test = (dateJSON) => {
    const before = new Date(dateJSON);
    if (isNaN(before)) {
      return false;
    }
    return date.getTime() > before.getTime();
  };
  const beforeFormat = formatDate(date);
  return {
    error: (key) =>
      dateError(`key "${key}" must be before "${beforeFormat}"`),
    test,
  };
}

function afterDateValidator(date) {
  const test = (dateJSON) => {
    const after = new Date(dateJSON);
    if (isNaN(after)) {
      return false;
    }
    return date.getTime() < after.getTime();
  };
  const afterFormat = formatDate(date);
  return {
    error: (key) =>
      dateError(`key "${key}" must be after "${afterFormat}"`),
    test,
  };
}

function dateValidator() {
  return {
    error: (value) => dateError(`element "${value}" must be date`),
    test: (dateJSON) => !isNaN(new Date(dateJSON)),
  };
}

class DateType extends Type {
  constructor() {
    super();
    this.validators.push(dateValidator());
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
