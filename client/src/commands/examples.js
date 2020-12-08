import io from '../console';

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
