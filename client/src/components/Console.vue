<template>
<p id="console-io">
</p>
</template>

<script>
import io from '../console';

function showimg(args) {
  if (args.length <= 0) {
    io.writeColor('falta ', 'red');
    io.writeColor('la ', 'yellow');
    io.writeColor('url!!', 'green');
    io.writeln();
    return;
  }
  io.writeln(`<img src="${args[0]}"/>`);
}

function showWelcome() {
  io.write('Hello! You can use ');
  io.writeColor('showimg ', 'orange');
  io.write('and ');
  io.writeColor('clear ', 'red');
  io.writeln('commands!');
}

export default {
  name: 'Console',
  mounted() {
    io.mount();
    showWelcome();
    io.startHandlingKeyEvents();
    io.onCommand('showimg', showimg);
    io.onCommand('clear', io.clear.bind(io));
  },
};
</script>

<style scoped>
#console-io {
  overflow: scroll;
  height: 98%;
  font-family: cascadia;
}

#console-io::after {
  content: "_";
  opacity: 0;

  animation-name: blink;
  animation-timing-function: linear;
  animation-iteration-count: infinite;
  animation-duration: 1s;
}

@keyframes blink {
  from { opacity: 0; }
  to { opacity: 1; }
}
</style>
