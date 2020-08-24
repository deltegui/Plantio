<template>
<div id="main-container">
  <Game/>
  <Console/>
</div>
</template>

<script>
import Game from './components/Game.vue';
import Console from './components/Console.vue';

export default {
  name: 'App',
  components: {
    Game,
    Console,
  },
};
</script>

<style>
body {
  padding: 0px;
  margin: 0px;
}

.left-panel {
  float: left;
}

#main-container {
  margin-left: auto;
  margin-right: auto;
  width: 1460px;
}
</style>
