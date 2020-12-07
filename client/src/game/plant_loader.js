import Entity from './entity';
import Plant from './plant';
import store from '../store';
import io from '../console';

const plantTypes = [
  {
    key: 'wheat',
    image: 'wheat.png',
  },
];

const plantConfig = {
  frameWidth: 189,
  frameHeight: 279,
};

function letterPositionToCoordinate({ x, y }) {
  const numberX = parseInt(y, 10);
  switch (x) {
    case 'a': case 'A':
      return { x: numberX, y: 3 };
    case 'b': case 'B':
      return { x: numberX, y: 2 };
    case 'c': case 'C':
      return { x: numberX, y: 1 };
    case 'd': case 'D':
      return { x: numberX, y: 0 };
    default:
      return false;
  }
}

const dscHelp = `Describes a plant. You must pass two parameters:
a letter and a number indicating plant coordinates. For example:
 "dsc A 1" or dsc c 0`;

export default class PlantLoader extends Entity {
  constructor(game) {
    super();
    this.game = game;
    this.plants = [];
  }

  getPlantForPosition({ x, y }) {
    const matched = this.plants.filter(({ position }) => position.x === x && position.y === y);
    return matched.length === 0 ? false : matched[0];
  }

  load() {
    plantTypes.forEach(({ key, image }) => this.game.load.spritesheet(key, image, plantConfig));
    io.onCommand('dsc', {
      help: dscHelp,
      handle: this.handleDescribePlant.bind(this),
    });
  }

  create() {
    store.save.forEach(this.addPlant.bind(this));
  }

  handleDescribePlant(args) {
    if (args.length < 2) {
      io.writeColor('You must pass plant coordinates. For example: "dsc A 1" or "dsc b 3"');
      io.writeln();
      return;
    }
    const [x, y] = args;
    const numericPos = letterPositionToCoordinate({ x, y });
    if (!numericPos) {
      io.writeColor(`Invalid position ${x} ${y}`, 'red');
      io.writeln();
      return;
    }
    const description = this.getPlantForPosition(numericPos);
    if (!description) {
      io.writeColor(`Empty! (${x} ${y})`, 'orange');
      io.writeln();
      return;
    }
    description.emphasis();
    io.writeln(`Plant ${description.plantID}`);
    io.writeln(`Phase: ${description.phase}`);
  }

  addPlant(plantInfo) {
    const pl = new Plant(this.game, plantInfo);
    pl.create();
    this.plants.push(pl);
  }
}
