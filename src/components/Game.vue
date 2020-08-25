<template>
  <canvas id="gameCanvas"></canvas>
</template>

<script>
import createGame from '../game';

export default {
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
  padding-bottom: 30vw;
  padding-top: 5px;
}

@media only screen and (max-width: 890px) {
  #gameCanvas {
    width: 90vw;
    margin-left: auto;
    margin-right: auto;
    padding-bottom: 0vw;
    padding-top: 0px;
  }
}
</style>
