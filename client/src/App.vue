<template>
<main>
  <DayNightCycle/>
  <Rain/>
  <LogoPanel/>
  <InfoPanel/>
  <StorePanel/>
  <div id="main-container">
    <Game/>
    <LeftPanel/>
  </div>
</main>
</template>

<script>
import Game from './components/Game.vue';
import LeftPanel from './components/LeftPanel.vue';
import DayNightCycle from './components/DayNightCycle.vue';
import Rain from './components/Rain.vue';
import InfoPanel from './components/InfoPanel.vue';
import StorePanel from './components/StorePanel.vue';
import LogoPanel from './components/LogoPanel.vue';

export default {
  name: 'App',
  components: {
    Game,
    LeftPanel,
    DayNightCycle,
    Rain,
    InfoPanel,
    StorePanel,
    LogoPanel,
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
