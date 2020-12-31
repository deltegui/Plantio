import io from '../console';
import plantService from '../logic/plant.service';
import gameService from '../logic/game.service';
import inputService from '../logic/input.service';
import userService from '../logic/user.service';

function parseInputCoordinate({ x, y }) {
  try {
    return inputService.parseInputCoordinate({ x, y });
  } catch (err) {
    io.writeColor(err.msg, 'red');
    io.writeln();
    return false;
  }
}

function parsePositionFromArgs(args) {
  if (args.length < 2) {
    io.writeColor('You must pass plant coordinates. For example: "dsc A 1" or "dsc b 3"<br>', 'red');
    return false;
  }
  const [x, y] = args;
  const numericPos = parseInputCoordinate({ x, y });
  if (!numericPos) {
    return false;
  }
  return numericPos;
}

function showEmptyPosition({ x, y }) {
  io.writeColor(`Empty! (${x} ${y})`, 'orange');
  io.writeln();
}

function callExtractingPosFromArgs(args, fn) {
  const pos = parsePositionFromArgs(args);
  if (!pos) return;
  try {
    fn(pos);
  } catch (err) {
    io.writeColor(`${err.msg}<br>`, 'orange');
  }
}

io.onCommand('show', {
  help: `Show planted plant. You must pass two parameters:
  a letter and a number indicating plant coordinates.<br>
  Usage: show [x] [y]<br>
  Example: show c 0`,
  handle(args) {
    const pos = parsePositionFromArgs(args);
    if (!pos) return;
    const plant = plantService.getForPosition(pos);
    if (!plant) {
      showEmptyPosition(pos);
      return;
    }
    io.writeln(`Plant ${plant.plant}`);
    io.writeln(`Phase: ${plant.phase}`);
    io.writeln(`Humidity: ${plant.humidity}%`);
    io.writeln(`Wet?: ${plant.watered === 'wet' ? 'Yes' : 'No'}`);
    try {
      plantService.emphasisForPosition(pos);
    } catch (err) {
      io.writeColor(`${err.msg}<br>`, 'orange');
    }
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
    try {
      userService.substractFrombag(name);
      plantService.add(name, numericPos);
    } catch (err) {
      io.writeColor(`${err.msg}<br>`, 'red');
    }
  },
});

io.onCommand('water', {
  help: `Waters plant. You need to pass the coordinate where
  the plant lives.<br>
  Usage: water [x] [y]<br>
  Example: water d 3`,
  handle(args) {
    callExtractingPosFromArgs(args, plantService.waterForPosition.bind(plantService));
  },
});

io.onCommand('ls', {
  help: `List all planted plants`,
  handle() {
    plantService.getAll().forEach((p) => {
      io.writeColor(`(${inputService.passNumberToLetter(p.position.x)}, ${p.position.y}) `, 'orange');
      io.writeln(p.plant);
    });
  },
});

io.onCommand('avl', {
  help: `List all available plants names`,
  handle() {
    gameService.plantTypes.forEach(({ key }) => io.writeColor(`${key}<br>`, 'green'));
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
    try {
      const {
        key,
        image,
        description,
        temperature,
        humidityDeath,
        humidityGrowth,
      } = plantService.getPlantTypeForName(name);
      io.writeColor(`<h3>${key}</h3><br>`, 'green');
      io.writeln(description);
      io.writeln(`<img src="/${image}" style="width: 100%">`);
      io.writeColor(`The minimal temperature to live is ${temperature.min}ºC<br>`, 'darkcyan');
      io.writeln();
      io.writeColor(`The maximum temperature to live is ${temperature.max}ºC<br>`, 'orange');
      io.writeln();
      io.writeln(`If this plant reaches a humidity lower or equal to ${humidityDeath}% dies!`);
      io.writeln();
      io.writeln(`This plant grows if reaches a humidity greater or equal to ${humidityGrowth}%`);
    } catch (err) {
      io.writeColor(`${err.msg}<br>`, 'red');
    }
  },
});

io.onCommand('rm', {
  help: `Removes a plant. You need to pass the coordinate where
  the plant lives.<br>
  Usage: rm [x] [y]<br>
  Example: rm d 3`,
  handle(args) {
    callExtractingPosFromArgs(args, plantService.deleteForPosition.bind(plantService));
  },
});

io.onCommand('recoll', {
  help: `Recollects a plant. It'll give you seeds. You need to pass the coordinate where
  the plant lives.<br>
  Usage: recoll [x] [y]<br>
  Example: recoll d 3`,
  handle(args) {
    const pos = parsePositionFromArgs(args);
    if (!pos) return;
    try {
      const amount = plantService.recollectPlant(pos);
      io.writeln(`Plant gives you ${amount} seeds!`);
    } catch (err) {
      io.writeColor(`${err.msg}<br>`, 'orange');
    }
  },
});

if (process.env.NODE_ENV !== 'production') {
  io.onCommand('grow', {
    help: `[DEBUG] Grows a plant. You need to pass the coordinate where
    the plant lives.<br>
    Usage: grow [x] [y]<br>
    Example: grow d 3`,
    handle(args) {
      callExtractingPosFromArgs(args, plantService.nextPhaseForPosition.bind(plantService));
    },
  });

  io.onCommand('dry', {
    help: `[DEBUG] Dry a plant. You need to pass the coordinate where
    the plant lives.<br>
    Usage: dry [x] [y]<br>
    Example: dry d 3`,
    handle(args) {
      callExtractingPosFromArgs(args, plantService.dryForPosition.bind(plantService));
    },
  });
}
