import CropCoordinate from './coordinate';
import Flowerpot from './flowerpot';
import movable from './movable';
import Entity from './entity';

export default class Crop extends Entity {
  static get size() {
    return CropCoordinate.MaxSize;
  }

  constructor(game) {
    super();
    this.game = game;
    this.mazetas = [];
  }

  load() {
    this.game.load.image('mazeta', 'mazeta.png');
    this.game.load.image('mazetaSeca', 'mazeta_seca.png');
  }

  create() {
    this.createMatrix();
    movable.add(this.move.bind(this));
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

  move(movement) {
    for (let i = 0; i < this.mazetas.length; i++) {
      const current = this.mazetas[i];
      current.move(movement);
    }
  }
}
