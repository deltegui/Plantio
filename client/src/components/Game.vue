<template>
  <canvas id="gameCanvas"></canvas>
</template>

<script>
import createGame from '../game';

export default {
  name: 'Game',
  mounted: () => {
    const canvas = document.getElementById('gameCanvas');
    const size = {
      height: 826,
      width: 1000,
    };
    createGame(canvas, size);
  },
};
</script>

<style scoped>
#gameCanvas {
  max-height: 100vh;
  margin-top: auto;
  margin-bottom: auto;
}

@media only screen and (max-width: 890px) {
  #gameCanvas {
    width: 90vw;
    margin-left: auto;
    margin-right: auto;
    margin-top: 0px;
  }
}
</style>
