import io from '../console';
import api from '../api';
import store from '../store';

io.onCommand('showimg', {
  help: `shows a img using it's URL. <br/>
Usage: showimg [url]. <br/>
Example: showimg http://localhost:8080/Mazeta.png`,
  handle(args) {
    if (args.length <= 0) {
      io.writeColor('falta ', 'red');
      io.writeColor('la ', 'yellow');
      io.writeColor('url!!', 'green');
      io.writeln();
      return;
    }
    io.writeln(`<img src="${args[0]}"/>`);
  },
});

io.onCommand('save', {
  help: 'Saves your game',
  async handle() {
    return api.game.save(store.user.token, store.save)
      .then(() => io.writeln('Game saved!'))
      .catch(() => io.writeColor('Failed!<br>', 'red'));
  },
});
