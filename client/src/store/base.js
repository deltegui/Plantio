import Vue from 'vue';

export default Vue.observable({
  logged: false,
  user: {
    name: '',
    money: 0,
    bag: [],
    token: '',
  },
  weather: {
    poblation: 'Unkown',
    temperature: 0,
    state: 'rainy',
    sunset: 0,
    sunrise: 0,
  },
  save: [],
  events: [],
});
