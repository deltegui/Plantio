import io from '../console';
import api from '../api';
import store from '../store';

io.onCommand('save', {
  help: 'Saves your game',
  async handle() {
    return api.game.save(store.user.token, store.save)
      .then(() => io.writeln('Game saved!'))
      .catch(() => io.writeColor('Failed!<br>', 'red'));
  },
});
