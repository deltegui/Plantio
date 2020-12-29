import io from '../console';
import gameService from '../logic/game.service';
import userService from '../logic/user.service';

io.onCommand('save', {
  help: 'Saves your game',
  async handle() {
    return gameService.saveGame()
      .then(() => io.writeln('Game saved!'))
      .catch(() => io.writeColor('Failed!<br>', 'red'));
  },
});

if (process.env.NODE_ENV !== 'production') {
  io.onCommand('add-bag', {
    help: `[DEBUG] add bag an item.
    Usage: add-bag [item-name]
    `,
    handle(args) {
      if (args.lenght <= 0) {
        io.writeColor('You must pass an item name', 'red');
        return;
      }
      const itemName = args[0];
      userService.addToBag(itemName);
      io.writeColor('Now bag have:<br>', 'DarkSeaGreen');
      io.writeln(JSON.stringify(userService.getBag()));
    },
  });

  io.onCommand('sub-bag', {
    help: `[DEBUG] substracts an item from bag.
    Usage: sub-bag [item-name]
    `,
    handle(args) {
      if (args.lenght <= 0) {
        io.writeColor('You must pass an item name', 'red');
        return;
      }
      const itemName = args[0];
      userService.substractFrombag(itemName);
      io.writeColor('Now bag have:<br>', 'DarkSeaGreen');
      io.writeln(JSON.stringify(userService.getBag()));
    },
  });
}
