import io from '../console';
import gameService from '../logic/game.service';
import userService from '../logic/user.service';
import storeService from '../logic/store.service';

function parseAmount(rawAmount) {
  const value = parseInt(rawAmount, 10);
  if (isNaN(value)) {
    return false;
  }
  if (value <= 0) {
    return false;
  }
  return value;
}

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
      if (args.lenght < 1) {
        io.writeColor('You must pass an item name', 'red');
        return;
      }
      const itemName = args[0];
      userService.substractFrombag(itemName);
      io.writeColor('Now bag have:<br>', 'DarkSeaGreen');
      io.writeln(JSON.stringify(userService.getBag()));
    },
  });

  io.onCommand('buym', {
    help: `[DEBUG] buy manually from store
    Usage: buym [item] [amount]`,
    async handle(args) {
      if (args.length < 2) {
        io.writeColor('You must pass an item name and an amount to buy<br>', 'red');
        return;
      }
      const [item, rawAmount] = args;
      const amount = parseAmount(rawAmount);
      if (!amount) {
        io.writeColor('Amount must be a positive number!<br>', 'red');
        return;
      }
      try {
        await storeService.buy({ item, amount });
      } catch (err) {
        io.writeColor(`${err.msg}<br>`, 'red');
      }
    },
  });

  io.onCommand('sellm', {
    help: `[DEBUG] sell manually from your bag
    Usage: sellm [item] [amount]`,
    async handle(args) {
      if (args.length < 2) {
        io.writeColor('You must pass an item name and an amount to buy<br>', 'red');
        return;
      }
      const [item, rawAmount] = args;
      const amount = parseAmount(rawAmount);
      if (!amount) {
        io.writeColor('Amount must be a positive number!<br>', 'red');
        return;
      }
      try {
        await storeService.sell({ item, amount });
      } catch (err) {
        io.writeColor(`${err.msg}<br>`, 'red');
      }
    },
  });
}
