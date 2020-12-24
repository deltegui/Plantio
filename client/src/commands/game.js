import io from '../console';
import gameService from '../logic/game.service';

io.onCommand('save', {
  help: 'Saves your game',
  async handle() {
    return gameService.saveGame()
      .then(() => io.writeln('Game saved!'))
      .catch(() => io.writeColor('Failed!<br>', 'red'));
  },
});
