<template>
<div id="text-console">
  <p id="console-io">
  </p>
</div>
</template>

<script>
export default {
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
        return;
      }
      write(String.fromCharCode(evt.keyCode));
    };
  },
};
</script>

<style scoped>
@font-face {
  font-family: consoles;
  src: url(/console.ttf);
}

#text-console {
  box-sizing: border-box;

  background-color: #272727;
  color: #E5E5E5;

  widows: 430px;
  min-width: 430px;
  height: 90vh;
  margin-top: 5vh;
  margin-right: 5px;
  padding: 5px;
  border-radius: 10px;

  float: left;
  overflow: scroll;

  font-family: consoles;
}

#text-console ::after {
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
