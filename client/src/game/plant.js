import Entity from './entity';
import movable from './movable';
import CropCoordinate from './coordinate';

function calculatePlantPosition(x, y) {
  const position = new CropCoordinate(x, y).toRealPosition();
  return {
    x: position.x + 55,
    y: position.y - 130,
  };
}

export default class Plant extends Entity {
  static phaseLimit = 5;

  static dryState = 6;

  constructor(game, {
    plant,
    position,
    phase,
  }) {
    super();
    this.game = game;
    this.plantID = plant;
    this.sprite = null;
    this.phase = phase;
    this.position = position;
    this.coordinate = calculatePlantPosition(position.x, position.y);
  }

  create() {
    const { x, y } = this.coordinate;
    this.sprite = this.game.add.sprite(x, y, this.plantID, this.phase).setOrigin(0, 0);
    movable.add(this.move.bind(this));
  }

  move(movement) {
    this.sprite.y += movement;
  }

  water() {
    if (this.phase < Plant.phaseLimit) {
      this.sprite.setFrame(++this.phase);
    }
  }

  dry() {
    this.phase = Plant.dryState;
    this.sprite.setFrame(Plant.dryState);
  }

  emphasis() {
    const transormations = [5, 5, 5, 5, -5, -5, -5, -5, -5, -5, -5, -5, 5, 5, 5, 5];
    let current = 0;
    const interval = setInterval(() => {
      this.sprite.x += transormations[current];
      current++;
      if (current >= transormations.length) {
        clearInterval(interval);
      }
    }, 50);
  }

  destroy() {
    this.sprite.destroy();
  }
}
