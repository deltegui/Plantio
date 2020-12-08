import io from '../console';
import CropCoordinate from '../game/coordinate';
import plantTypes from '../game/plant_types';
import {
  getPlantForPosition,
  getAllPlants,
  addPlant,
} from '../game';

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

function plantNameExists(name) {
  const names = plantTypes.map(({ key }) => key).filter((key) => key === name);
  return names.length > 0;
}

io.onCommand('dsc', {
  help: `Describes a plant. You must pass two parameters:
  a letter and a number indicating plant coordinates. For example:
    "dsc A 1" or "dsc c 0"`,
  handle(args) {
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
    const description = getPlantForPosition(numericPos);
    if (!description) {
      io.writeColor(`Empty! (${x} ${y})`, 'orange');
      io.writeln();
      return;
    }
    description.emphasis();
    io.writeln(`Plant ${description.plantID}`);
    io.writeln(`Phase: ${description.phase}`);
  },
});

io.onCommand('plant', {
  help: `Plants a new plant. You need to pass the plant name and the coordinate where
  the plant will live. For example: plant wheat d 3`,
  handle(args) {
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
    if (getPlantForPosition(numericPos)) {
      io.writeColor(`Plant already planted! (${x} ${y})`, 'orange');
      io.writeln();
      return;
    }
    addPlant({
      plant: name,
      position: numericPos,
      phase: 0,
    });
  },
});

io.onCommand('water', {
  help: `Waters plant. You need to pass the coordinate where
  the plant lives. For example: water d 3`,
  handle(args) {
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
    const plantToWater = getPlantForPosition(numericPos);
    if (!plantToWater) {
      io.writeColor(`Empty! (${x} ${y}) `, 'orange');
      io.writeln();
      return;
    }
    plantToWater.water();
  },
});

io.onCommand('ls', {
  help: `List all plants`,
  handle() {
    getAllPlants().forEach((p) => {
      io.writeColor(`(${passNumberToLetter(p.position.x)}, ${p.position.y}) `, 'orange');
      io.writeln(p.plantID);
    });
  },
});
