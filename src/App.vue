<template>
<div>
   <DayNightCycle/>
  <div id="main-container">
    <Game/>
    <Console/>
  </div>
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
#main-container {
  max-width: 1430px;
  margin-left: auto;
  margin-right: auto;
  display: flex;

  z-index: 1000;
}

@media only screen and (max-width: 890px) {
  #main-container {
    flex-direction: column;
  }
}
</style>
