<template>
  <div id="bg-filter"></div>
</template>

<style>
body {
  margin: 0;
  padding: 0;
  height: 100vh;
  width: 100vw;
  background-image: url('/stars.jpg');
  background-size: cover;
  position: relative;
  overflow: hidden;
}

#bg-filter {
  margin: 0;
  padding: 0;
  height: 100vh;
  width: 100vw;

  position: absolute;
  left: 0px;
  top: 0px;

  z-index: -1;
}
</style>

<script>
const colours = [
  { upper: 'rgba(0,0,12,0)', bottom: 'rgba(0,0,12,0)' },
  { upper: 'rgba(2,1,17,0)', bottom: 'rgba(25,22,33,.3)' },
  { upper: 'rgba(2,1,17,0)', bottom: 'rgba(25,22,33,.3)' },
  { upper: 'rgba(2,1,17,0)', bottom: 'rgba(32,32,44,.8)' },
  { upper: 'rgba(32,32,44,0.5)', bottom: 'rgb(58,58,82)' },
  { upper: 'rgba(64,64,92,.8)', bottom: 'rgb(81,81,117)' },
  { upper: 'rgb(74,73,105)', bottom: 'rgb(138,118,171)' },
  { upper: 'rgb(117,122,191)', bottom: 'rgb(205,130,160)' },
  { upper: 'rgb(130,173,219)', bottom: 'rgb(234,176,209)' },
  { upper: 'rgb(148,197,248)', bottom: 'rgb(235,178,177)' },
  { upper: 'rgb(183,234,255)', bottom: 'rgb(177,181,234)' },
  { upper: 'rgb(144,223,254)', bottom: 'rgb(148,223,255)' },
  { upper: 'rgb(87,193,235)', bottom: 'rgb(103,209,251)' },
  { upper: 'rgb(45,145,194)', bottom: 'rgb(56,163,209)' },
  { upper: 'rgb(36,115,171)', bottom: 'rgb(36,111,168)' },
  { upper: 'rgb(30,82,142)', bottom: 'rgb(30,82,142)' },
  { upper: 'rgb(30,82,142)', bottom: 'rgb(91,121,131)' },
  { upper: 'rgb(21,66,119)', bottom: 'rgb(157,166,113)' },
  { upper: 'rgba(22,60,82,0.8)', bottom: 'rgb(233,206,93)' },
  { upper: 'rgba(7,27,38,.5)', bottom: 'rgb(178,99,57)' },
  { upper: 'rgba(1,10,16,.3)', bottom: 'rgb(47,17,7)' },
  { upper: 'rgba(9,4,1,0)', bottom: 'rgb(36,14,3)' },
  { upper: 'rgba(0,0,12,0)', bottom: 'rgb(47,17,7)' },
  { upper: 'rgba(0,0,12,0)', bottom: 'rgba(75,29,6,.4)' },
  { upper: 'rgba(0,0,12,0)', bottom: 'rgba(21,8,0,0)' },
];

function nowInUnix() {
  const now = new Date();
  return now.getTime() / 1000;
}

function quarterHoursPassedFrom(now, moment) {
  const quarterHoursFromSeconds = 900;
  const timePassed = now - moment;
  return Math.round(timePassed / quarterHoursFromSeconds);
}

function gradientForSunrise(now, sunrise) {
  const quarterHourPassed = quarterHoursPassedFrom(now, sunrise);
  return quarterHourPassed >= 12 ? colours[12] : colours[quarterHourPassed];
}

function gradientForSunset(now, sunset) {
  const startSunset = 12;
  const quarterHourPassed = startSunset + quarterHoursPassedFrom(now, sunset);
  return quarterHourPassed >= 24 ? colours[24] : colours[quarterHourPassed];
}

function getGradient({ sunrise, sunset }) {
  const nowTimestamp = nowInUnix();
  if (nowTimestamp < sunrise) {
    return colours[0];
  }
  if (nowTimestamp > sunset) {
    return gradientForSunset(nowTimestamp, sunset);
  }
  return gradientForSunrise(nowTimestamp, sunrise);
}

export default {
  data() {
    return {
      weather: this.$store.weather,
    };
  },
  watch: {
    weather: {
      handler(newWeather) {
        const filter = document.getElementById('bg-filter');
        const gradient = getGradient(newWeather);
        filter.style.backgroundImage = `linear-gradient(${gradient.upper}, ${gradient.bottom})`;
      },
      deep: true,
    },
  },
};
</script>
