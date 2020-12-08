import Vue from 'vue';
import App from './App.vue';

Vue.config.productionTip = false;

new Vue({
  render: (h) => h(App),
}).$mount('#app');

/* eslint-disable */
window.addEventListener('beforeunload', (e) => {
  e.preventDefault();
  return 'Please ensure your game is saved!';
});
