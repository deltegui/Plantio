/* eslint-disable max-classes-per-file */

export class InvalidPlantName {
  constructor(name) {
    this.msg = `Plant name "${name}" does not exist`;
  }
}

export class AlreadyExists {
  constructor({ x, y }) {
    this.msg = `Plant already planted! (${x}, ${y})`;
  }
}

export class NotFound {
  constructor({ x, y }) {
    this.msg = `Empty position! (${x} ${y})`;
  }
}

export class UnknownPlantType {
  constructor(name) {
    this.msg = `Unknown plant name "${name}"`;
  }
}

export class CannotRecollect {
  constructor(name) {
    this.msg = `Cannot recollet plant "${name}". Isn't plant at maximum growth or is it dead?`;
  }
}
