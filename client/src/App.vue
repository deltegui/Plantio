<template>
<main>
  <DayNightCycle/>
  <InfoPanel/>
  <div id="main-container">
    <Game/>
    <LeftPanel/>
  </div>
</main>
</template>

<script>
// import './store';
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

@font-face {
  font-family: cascadia;
  src: url(/CascadiaMono.ttf);
}

body {
  font-family: consoles;
}

#main-container {
  max-width: 1430px;
  margin-left: auto;
  margin-right: auto;
  display: flex;
}

@media only screen and (max-width: 890px) {
  #main-container {
    flex-direction: column;
    height: 100vh;
  }
}
</style>
