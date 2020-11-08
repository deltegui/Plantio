import ConsoleIO from './ConsoleIO';

const io = new ConsoleIO();

io.onCommand('clear', {
  help: 'clears the terminal',
  handle: io.clear.bind(io),
});

io.onCommand('help', {
  help: 'shows all available commands',
  handle() {
    Object.keys(io.commandHandlers).forEach((command) => {
      io.writeColor(`${command}: `, 'orange');
      io.writeln(io.commandHandlers[command].help);
    });
  },
});

export default io;
