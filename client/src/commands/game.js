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
      .catch((err) => io.writeColor(`Failed: ${err.message}!<br>`, 'red'));
  },
});

io.onCommand('bag', {
  help: 'Shows items in your bag',
  handle() {
    const occupation = userService.getBagOccupation();
    let color = 'green';
    if (occupation > 60 && occupation < 80) {
      color = 'orange';
    } else if (occupation >= 80) {
      color = 'red';
    }
    io.writeColor(`${occupation}% (${userService.sumElementsInBag()}/${userService.getBagMaxSize()}) full bag!<br>`, color);
    userService.getBag().forEach((item) => {
      io.writeColor(item.item, 'green');
      io.write(': ');
      io.writeColor(item.amount, 'orange');
      io.writeln();
    });
  },
});

io.onCommand('sell', {
  help: `sell items from your bag
  Usage: sell [item] [amount]`,
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

if (process.env.NODE_ENV !== 'production') {
  io.onCommand('add-bag', {
    help: `[DEBUG] add bag an item. Optionally you can pass the amount.
    Usage: add-bag [item-name] or add-bag [item-name] [amount]
    `,
    handle(args) {
      if (args.lenght <= 0) {
        io.writeColor('You must pass an item name', 'red');
        return;
      }
      const [itemName, times] = args;
      const max = times || 0;
      for (let i = 0; i <= max; i++) {
        userService.addToBag(itemName);
      }
      io.writeColor('Now bag have:<br>', 'DarkSeaGreen');
      io.writeln(JSON.stringify(userService.getBag()));
    },
  });

  io.onCommand('sub-bag', {
    help: `[DEBUG] substracts an item from bag. Optionally you can pass the amount.
    Usage: sub-bag [item-name] or sub-bag [item-name] [amount]
    `,
    handle(args) {
      if (args.lenght < 1) {
        io.writeColor('You must pass an item name', 'red');
        return;
      }
      const [itemName, times] = args;
      const max = times || 0;
      for (let i = 0; i <= max; i++) {
        userService.substractFrombag(itemName);
      }
      io.writeColor('Now bag have:<br>', 'DarkSeaGreen');
      io.writeln(JSON.stringify(userService.getBag()));
    },
  });

  io.onCommand('buy', {
    help: `[DEBUG] buy items from store
    Usage: buy [item] [amount]`,
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
}
