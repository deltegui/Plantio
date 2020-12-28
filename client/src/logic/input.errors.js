/* eslint-disable max-classes-per-file */

export class InvalidYPosition {
  constructor(y) {
    this.msg = `Invalid position for y: "${y}". Must be 0, 1, 2 or 3`;
  }
}

export class InvalidXPosition {
  constructor(x) {
    this.msg = `Invalid position for x: "${x}". Must be A, B, C or D`;
  }
}
