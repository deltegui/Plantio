import io from '../console';
import CropCoordinate from '../game/coordinate';
import plantTypes from '../game/plant_types';
import {
  getPlantForPosition,
  getAllPlants,
  addPlant,
} from '../game';
import {
  addPlantSave,
  updatePlantSave,
  deletePlantSave,
} from '../store/save';

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

function getPlantFromArgsPosition(args) {
  if (args.length < 2) {
    io.writeColor('You must pass plant coordinates. For example: "dsc A 1" or "dsc b 3"<br>', 'red');
    return false;
  }
  const [x, y] = args;
  const numericPos = parseInputCoordinate({ x, y });
  if (!numericPos) {
    return false;
  }
  const description = getPlantForPosition(numericPos);
  if (!description) {
    io.writeColor(`Empty! (${x} ${y})`, 'orange');
    io.writeln();
    return false;
  }
  return description;
}

io.onCommand('show', {
  help: `Show planted plant. You must pass two parameters:
  a letter and a number indicating plant coordinates.<br>
  Usage: show [x] [y]<br>
  Example: show c 0`,
  handle(args) {
    const description = getPlantFromArgsPosition(args);
    if (!description) return;
    description.emphasis();
    io.writeln(`Plant ${description.plantID}`);
    io.writeln(`Phase: ${description.phase}`);
  },
});

io.onCommand('plant', {
  help: `Plants a new plant. You need to pass the plant name and the coordinate where
  the plant will live.<br>
  Usage: plant [name] [x] [y]<br>
  Example: plant wheat d 3`,
  handle(args) {
    if (args.length < 3) {
      io.writeColor('Usage: plant [name] [x coordinate] [y coordinate]. Example: plant wheat d 2<br>', 'red');
      return;
    }
    const [name, x, y] = args;
    const numericPos = parseInputCoordinate({ x, y });
    if (!numericPos) {
      return;
    }
    if (!plantNameExists(name)) {
      io.writeColor(`Plant name "${name}" does not exist<br>`, 'red');
      return;
    }
    if (getPlantForPosition(numericPos)) {
      io.writeColor(`Plant already planted! (${x} ${y})<br>`, 'orange');
      return;
    }
    const plant = {
      plant: name,
      position: numericPos,
      phase: 0,
    };
    addPlant(plant);
    addPlantSave(plant);
  },
});

io.onCommand('water', {
  help: `Waters plant. You need to pass the coordinate where
  the plant lives.<br>
  Usage: water [x] [y]<br>
  Example: water d 3`,
  handle(args) {
    const plantToWater = getPlantFromArgsPosition(args);
    if (!plantToWater) return;
    plantToWater.water();
    updatePlantSave({
      phase: plantToWater.phase,
      plant: plantToWater.plantID,
      position: {
        x: plantToWater.position.x,
        y: plantToWater.position.y,
      },
    });
  },
});

io.onCommand('ls', {
  help: `List all planted plants`,
  handle() {
    getAllPlants().forEach((p) => {
      io.writeColor(`(${passNumberToLetter(p.position.x)}, ${p.position.y}) `, 'orange');
      io.writeln(p.plantID);
    });
  },
});

io.onCommand('avl', {
  help: `List all available plants names`,
  handle() {
    plantTypes.forEach(({ key }) => io.writeColor(`${key}<br>`, 'green'));
  },
});

io.onCommand('dsc', {
  help: `Describes a plant. You must pass a available plant name. Use avl
  command to show available plants.<br>
  Usage: dsc [name]<br>
  Example: dsc wheat`,
  handle(args) {
    if (args.length < 1) {
      io.writeColor('You must pass a plant name<br>', 'red');
      return;
    }
    const [name] = args;
    const matchPlants = plantTypes.filter(({ key }) => key === name);
    if (matchPlants.length <= 0) {
      io.writeColor(`Unknown plant name "${name}"<br>`, 'red');
      return;
    }
    const { key, image, description } = matchPlants[0];
    io.writeColor(`<h3>${key}</h3><br>`, 'green');
    io.writeln(description);
    io.writeln(`<img src="/${image}" style="width: 100%">`);
  },
});

io.onCommand('rm', {
  help: `Removes a plant. You need to pass the coordinate where
  the plant lives.<br>
  Usage: rm [x] [y]<br>
  Example: rm d 3`,
  handle(args) {
    const plantToKill = getPlantFromArgsPosition(args);
    if (!plantToKill) return;
    deletePlantSave(plantToKill.position);
  },
});

io.onCommand('dry', {
  help: `Dry a plant. You need to pass the coordinate where
  the plant lives.<br>
  Usage: dry [x] [y]<br>
  Example: dry d 3`,
  handle(args) {
    const plantToDry = getPlantFromArgsPosition(args);
    if (!plantToDry) return;
    plantToDry.dry();
    updatePlantSave({
      phase: plantToDry.phase,
      plant: plantToDry.plantID,
      position: {
        x: plantToDry.position.x,
        y: plantToDry.position.y,
      },
    });
  },
});
