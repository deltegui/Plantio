<template>
<div class="info-panel" v-if="$store.logged">
  <div class="info-line title">{{ $store.weather.poblation }}</div>
  <div class="info-line">
    <img class="weather" :src="weatherImage"/>
    <div class="temperature">{{ $store.weather.temperature }}ºC</div>
  </div>
</div>
</template>

<script>
import timeService from '../logic/time.service';

function isNightAndClear(weather) {
  const now = timeService.nowInUnix();
  return (now < weather.sunrise || now > weather.sunset) && weather.state === 'sunny';
}

export default {
  name: 'InfoPanel',
  computed: {
    weatherImage() {
      if (isNightAndClear(this.$store.weather)) return `/night.png`;
      return `/${this.$store.weather.state}.png`;
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
