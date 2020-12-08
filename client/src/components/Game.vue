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
  min-width: 450px;
  height: 55%;
  width: 100vw;
  margin-top: auto;
  margin-bottom: auto;
  margin-left: auto;
  margin-right: auto;
}

@media only screen and (max-width: 890px) {
  #gameCanvas {
    min-width: 0px;
    max-width: 600px;
    height: auto;
    margin-top: 0px;
  }
}
</style>
