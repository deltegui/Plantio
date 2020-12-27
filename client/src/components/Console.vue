<template>
<p id="console-io">
</p>
</template>

<script>
import io from '../console';
import '../commands';
import userService from '../logic/user.service';

export default {
  name: 'Console',
  mounted() {
    this.bootstrap();
  },
  methods: {
    bootstrap() {
      io.mount();
      this.showWelcome();
      io.startHandlingKeyEvents();
      if (process.env.NODE_ENV !== 'production') {
        io.onCommand('store', {
          help: 'Shows custom data store',
          handle: this.showStore.bind(this),
        });
      }
      io.onCommand('logout', {
        help: 'Log user out',
        handle: () => userService.logout(),
      });
    },

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
      io.writeColor('Dont forget to save your game!!!<br>', 'DarkCyan');
      io.writeln();
    },
  },
  watch: {
    '$store.events': {
      handler() {
        if (this.$store.events.length === 0) {
          io.writeColor('Nothing happened since last save!', 'MediumPurple');
          io.writeln();
          io.writeln();
        } else {
          io.writeColor('Messages from last save:<br>', 'MediumPurple');
          this.$store.events.forEach((evt) => {
            io.writeln(`* Plant in position (${evt.position.x}, ${evt.position.y}) is ${evt.eventType}`);
            io.writeln();
          });
        }
        io.write('> ');
      },
      deep: true,
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
