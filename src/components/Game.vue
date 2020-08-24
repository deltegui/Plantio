<template>
  <canvas id="gameCanvas"></canvas>
</template>

<script>
import createGame from '../game';

export default {
  mounted: () => {
    const canvas = document.getElementById('gameCanvas');
    createGame(canvas, {
      height: window.innerHeight,
    });
  },
};
</script>

<style scoped>
#gameCanvas {
  float: left;
}
</style>
