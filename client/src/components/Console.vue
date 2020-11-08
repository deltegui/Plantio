<template>
<p id="console-io">
</p>
</template>

<script>
import io from '../console';

const showimg = {
  help: `shows a img using it's URL. <br/>
Usage: showimg url. <br/>
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
};

export default {
  name: 'Console',
  mounted() {
    io.mount();
    this.showWelcome();
    io.startHandlingKeyEvents();
    io.onCommand('showimg', showimg);
    io.onCommand('store', {
      help: 'Shows custom data store',
      handle: this.showStore.bind(this),
    });
    io.onCommand('logout', {
      help: 'Log user out',
      handle: () => this.$actions.logout(),
    });
  },
  beforeDestroy() {
    io.stopHandlingKeyEvents();
  },
  methods: {
    showStore() {
      io.writeln(JSON.stringify(this.$store));
    },

    showWelcome() {
      io.write(`Hello ${this.$store.user.name}! You can use `);
      io.writeColor('help ', 'orange');
      io.writeln('to show all available commands!');
    },
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
