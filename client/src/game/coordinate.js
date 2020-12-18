import Flowerpot from './flowerpot';

export default class CropCoordinate {
  static get MaxSize() {
    return {
      x: 4,
      y: 4,
    };
  }

  static get MaxValue() {
    const size = CropCoordinate.MaxSize;
    return {
      x: size.x - 1,
      y: size.y - 1,
    };
  }

  constructor(x, y) {
    this.x = x;
    this.y = y;
  }

  isPosition({ x, y }) {
    return this.x === x && this.y === y;
  }

  toRealPosition() {
    const yMargin = 140;
    const linePos = this.calculateLinePosition();
    const yOffset = this.x * Flowerpot.size.y;
    return {
      x: linePos.x + (this.x * Flowerpot.size.x),
      y: yOffset + yMargin + this.y * Flowerpot.size.y,
    };
  }

  calculateLinePosition() {
    const lineStartOffset = CropCoordinate.MaxValue.y - this.y;
    return {
      x: lineStartOffset * Flowerpot.size.x,
      y: this.y * Flowerpot.size.y,
    };
  }
}
