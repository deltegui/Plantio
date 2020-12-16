import Vue from 'vue';

export default Vue.observable({
  logged: true,
  user: {
    name: '',
    token: '',
  },
  weather: {
    poblation: 'Unkown',
    temperature: 0,
    state: 'rainy',
    season: 'Unknown',
  },
  save: [],
});
