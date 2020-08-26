<template>
<div>
  <DayNightCycle/>
  <InfoPanel/>
  <div id="main-container">
    <Game/>
    <LeftPanel/>
  </div>
</div>
</template>

<script>
import Game from './components/Game.vue';
import LeftPanel from './components/LeftPanel.vue';
import DayNightCycle from './components/DayNightCycle.vue';
import InfoPanel from './components/InfoPanel.vue';

export default {
  name: 'App',
  components: {
    Game,
    LeftPanel,
    DayNightCycle,
    InfoPanel,
  },
};
</script>

<style>
@font-face {
  font-family: consoles;
  src: url(/console.ttf);
}

body {
  font-family: consoles;
}

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
