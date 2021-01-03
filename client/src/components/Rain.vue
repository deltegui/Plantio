<template>
  <div id="rain" :style="style" ></div>
</template>

<script>
export default {
  name: 'Rain',
  data() {
    return {
      opacity: 0,
      weather: this.$store.weather,
    };
  },
  watch: {
    weather: {
      handler(newWeather) {
        if (newWeather.state === 'rainy') {
          this.opacity = 2;
        } else {
          this.opacity = 0;
        }
      },
      deep: true,
    },
  },
  computed: {
    style() {
      return `opacity: .${this.opacity}`;
    },
  },
};
</script>

<style scoped>
#rain {
  margin: 0;
  padding: 0;
  height: 100vh;
  width: 100vw;

  position: absolute;
  left: 0px;
  top: 0px;

  background-image: url("/rain.png");
  animation: rain .3s linear infinite;

  z-index: 0;
}

@keyframes rain {
  from {
    background-position: 0% 0%;
  }

  to {
    background-position: 20% 100%;
  }
}
</style>
