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
    this.game.load.image('mazeta_seca', 'mazeta_seca.png');
  }

  create() {
    this.createMatrix(this.defaultFlowerpotProvider.bind(this));
    movable.add(this.move.bind(this));
  }

  defaultFlowerpotProvider(coordinate) {
    return new Flowerpot(this.game, coordinate);
  }

  createMatrix(flowerpotProvider) {
    for (let i = 0; i < Crop.size.x; i++) {
      this.createLine(i, flowerpotProvider);
    }
  }

  createLine(lineNumber, flowerpotProvider) {
    for (let i = 0; i < Crop.size.y; i++) {
      const coordinate = new CropCoordinate(lineNumber, i);
      const mazeta = flowerpotProvider(coordinate);
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

  reload(plants) {
    const wetPlants = plants.filter((p) => p.watered === 'wet');
    this.mazetas.forEach((m) => m.destroy());
    this.createMatrix((coord) => {
      for (let i = 0; i < wetPlants.length; i++) {
        if (coord.isPosition(wetPlants[i].position)) {
          return new Flowerpot(this.game, coord, { watered: true });
        }
      }
      return new Flowerpot(this.game, coord);
    });
  }
}
