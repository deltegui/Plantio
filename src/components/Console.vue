<template>
<p id="console-io">
</p>
</template>

<script>
export default {
  name: 'Console',
  mounted() {
    const consoleIO = document.getElementById('console-io');
    const write = (str) => {
      consoleIO.innerHTML += str;
    };
    write('> ');
    document.onkeydown = (evt) => {
      evt.preventDefault();
      if (evt.keyCode === 8) { // Backspace
        if (consoleIO.innerText.endsWith('> ')) {
          return;
        }
        consoleIO.innerHTML = consoleIO.innerHTML.substring(0, consoleIO.innerHTML.length - 1);
        return;
      }
      if (evt.keyCode === 13) { // Enter
        write('<br /> > ');
        consoleIO.scrollTop = consoleIO.scrollHeight;
        return;
      }
      write(String.fromCharCode(evt.keyCode));
    };
  },
};
</script>

<style scoped>
#console-io {
  overflow: scroll;
  height: 98%;
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

@media only screen and (max-width: 890px) {
  #text-console {
    widows: 100%;
    min-width: 100%;
    margin-top: 0vh;
    margin-right: 0px;
  }
}
</style>
