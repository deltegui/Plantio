<template>
<div id="main-container">
  <Game/>
  <Console/>
  <DayNightCycle/>
</div>
</template>

<script>
import Game from './components/Game.vue';
import Console from './components/Console.vue';
import DayNightCycle from './components/DayNightCycle.vue';

export default {
  name: 'App',
  components: {
    Game,
    Console,
    DayNightCycle,
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
