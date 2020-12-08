<template>
<p id="console-io">
</p>
</template>

<script>
import io from '../console';
import '../commands';

export default {
  name: 'Console',
  mounted() {
    io.mount();
    this.showWelcome();
    io.startHandlingKeyEvents();
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
      io.write('Hello ');
      io.writeColor(`<b><i>${this.$store.user.name}</i></b>`, 'pink');
      io.write('! You can use ');
      io.writeColor('help ', 'orange');
      io.write('to show all available commands! Type ');
      io.writeColor('logout ', 'green');
      io.writeln('to exit.');
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
