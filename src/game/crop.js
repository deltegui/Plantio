import CropCoordinate from './coordinate';
import Flowerpot from './flowerpot';

export default class Crop {
  static get size() {
    return CropCoordinate.MaxSize;
  }

  constructor(game) {
    this.game = game;
    this.mazetas = [];
  }

  load() {
    this.game.load.image('mazeta', 'mazeta.png');
    this.game.load.image('mazetaSeca', 'mazeta_seca.png');
  }

  create() {
    this.createMatrix();
    setInterval(this.move.bind(this), 1000);
  }

  createMatrix() {
    for (let i = 0; i < Crop.size.x; i++) {
      this.createLine(i);
    }
  }

  createLine(lineNumber) {
    for (let i = 0; i < Crop.size.y; i++) {
      const coordinate = new CropCoordinate(lineNumber, i);
      const mazeta = new Flowerpot(this.game, coordinate);
      mazeta.create();
      this.mazetas.push(mazeta);
    }
  }

  move() {
    for (let i = 0; i < this.mazetas.length; i++) {
      const current = this.mazetas[i];
      current.move();
    }
  }
}
