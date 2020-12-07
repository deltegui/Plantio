import Entity from './entity';
import Plant from './plant';
import store from '../store';
import io from '../console';
import CropCoordinate from './coordinate';

const plantTypes = [
  {
    key: 'wheat',
    image: 'wheat.png',
  },
];

function plantNameExists(name) {
  const names = plantTypes.map(({ key }) => key).filter((key) => key === name);
  return names.length > 0;
}

const plantConfig = {
  frameWidth: 189,
  frameHeight: 279,
};

function parseInputCoordinate({ x, y }) {
  const numy = parseInt(y, 10);
  if (isNaN(numy) || numy >= CropCoordinate.MaxSize.y) {
    io.writeColor(`Invalid position for y: "${y}". Must be 0, 1, 2 or 3`, 'red');
    io.writeln();
    return false;
  }
  switch (x) {
    case 'a': case 'A':
      return { x: numy, y: 3 };
    case 'b': case 'B':
      return { x: numy, y: 2 };
    case 'c': case 'C':
      return { x: numy, y: 1 };
    case 'd': case 'D':
      return { x: numy, y: 0 };
    default:
      io.writeColor(`Invalid position for x: "${x}". Must be A, B, C or D`, 'red');
      io.writeln();
      return false;
  }
}

function passNumberToLetter(number) {
  const letters = ['D', 'C', 'B', 'A'];
  return letters[number];
}

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
      help: `Describes a plant. You must pass two parameters:
      a letter and a number indicating plant coordinates. For example:
       "dsc A 1" or "dsc c 0"`,
      handle: this.handleDescribePlant.bind(this),
    });
    io.onCommand('plant', {
      help: `Plants a new plant. You need to pass the plant name and the coordinate where
      the plant will live. For example: plant wheat d 3`,
      handle: this.handlePlant.bind(this),
    });
    io.onCommand('water', {
      help: `Waters plant. You need to pass the coordinate where
      the plant lives. For example: water d 3`,
      handle: this.handleWaterPlant.bind(this),
    });
    io.onCommand('ls', {
      help: `List all plants`,
      handle: this.handleList.bind(this),
    });
  }

  create() {
    store.save.forEach(this.addPlant.bind(this));
  }

  addPlant(plantInfo) {
    const pl = new Plant(this.game, plantInfo);
    pl.create();
    this.plants.push(pl);
  }

  handleDescribePlant(args) {
    if (args.length < 2) {
      io.writeColor('You must pass plant coordinates. For example: "dsc A 1" or "dsc b 3"');
      io.writeln();
      return;
    }
    const [x, y] = args;
    const numericPos = parseInputCoordinate({ x, y });
    if (!numericPos) {
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

  handlePlant(args) {
    if (args.length < 3) {
      io.writeColor('Usage: plant [name] [x coordinate] [y coordinate]. Example: plant wheat d 2');
      io.writeln();
      return;
    }
    const [name, x, y] = args;
    const numericPos = parseInputCoordinate({ x, y });
    if (!numericPos) {
      return;
    }
    if (!plantNameExists(name)) {
      io.writeColor(`Plant name "${name}" does not exist`, 'red');
      io.writeln();
      return;
    }
    if (this.getPlantForPosition(numericPos)) {
      io.writeColor(`Plant already planted! (${x} ${y})`, 'orange');
      io.writeln();
      return;
    }
    this.addPlant({
      plant: name,
      position: numericPos,
      phase: 0,
    });
  }

  handleWaterPlant(args) {
    if (args.lengt < 2) {
      io.writeColor('You must pass plant coordinates. For example: "dsc A 1" or "dsc b 3"');
      io.writeln();
      return;
    }
    const [x, y] = args;
    const numericPos = parseInputCoordinate({ x, y });
    if (!numericPos) {
      return;
    }
    const plant = this.getPlantForPosition(numericPos);
    if (!plant) {
      io.writeColor(`Empty! (${x} ${y}) `, 'orange');
      io.writeln();
      return;
    }
    plant.water();
  }

  handleList() {
    this.plants.forEach((plant) => {
      io.writeColor(`(${passNumberToLetter(plant.position.x)}, ${plant.position.y}) `, 'orange');
      io.writeln(plant.plantID);
    });
  }
}
