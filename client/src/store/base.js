import Vue from 'vue';

const save = [
  {
    position: { x: 0, y: 0 },
    plant: 'wheat',
    phase: 3,
  },
];

export default Vue.observable({
  logged: false,
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
  save,
});
