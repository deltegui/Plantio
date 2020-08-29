<template>
<div class="info-panel">
  <div class="info-line title">{{ poblation }}</div>
  <div class="info-line">{{ season }}</div>
  <div class="info-line">
    <img class="weather" :src="weatherImage"/>
    <div class="temperature">{{ temperature }}ºC</div>
  </div>
</div>
</template>

<script>
export default {
  name: 'InfoPanel',
  data: () => ({
    poblation: 'Algete',
    temperature: 22,
    weather: 'rainy',
    season: 'Verano',
  }),
  computed: {
    weatherImage() {
      return `/${this.weather}.png`;
    },
  },
};
</script>

<style scoped>
.title {
  font-size: 30px;
}

.info-panel {
  position: absolute;
  left: 10px;
  top: 10px;

  width: 300px;
  height: 150px;

  font-size: 20px;

  color: white;
}

.info-line {
  margin-bottom: 10px;
  width: 100%;
  vertical-align: middle;
}

.weather {
  --img-size: 70px;
  float: left;
  width: var(--img-size);
  height: var(--img-size);
}

.temperature {
  padding-top: 20px;
  margin-left: 10px;
  float: left;
}

@media only screen and (max-width: 890px) {
  .title {
    font-size: 20px;
  }

  .info-panel {
    font-size: 15px;
  }

  .info-panel {
    display: block;
  }

  .weather {
    --img-size: 30px;
  }

  .temperature {
    padding-top: 3px;
  }
}
</style>
