import Vue from 'vue';

const save = [
  {
    position: { x: 0, y: 0 },
    plant: 'wheat',
    phase: 3,
  },
  {
    position: { x: 3, y: 3 },
    plant: 'wheat',
    phase: 2,
  },
  {
    position: { x: 2, y: 3 },
    plant: 'wheat',
    phase: 5,
  },
  {
    position: { x: 2, y: 2 },
    plant: 'wheat',
    phase: 4,
  },
  {
    position: { x: 0, y: 3 },
    plant: 'wheat',
    phase: 1,
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
