<template>
  <div class='store-card'>
    <img src="bag_cactus.png"/>
    <h4>Cactus seeds</h4>
    <p>2 <img src="credito.png"/> / unit</p>
    <span>
      <input type="number" class="field" min="1" value="0"/>
      <ButtonSpin text="Buy"/>
    </span>
  </div>
</template>

<script>
import ButtonSpin from './ButtonSpin.vue';

export default {
  name: 'StoreCard',
  components: {
    ButtonSpin,
  },
};
</script>

<style scoped>
.store-card {
  display: grid;
  grid-template-columns: auto;
  grid-template-rows: 150px 40px 50px 160px;

  background-color: #444444;

  justify-content: stretch;
  height: 360px;
  padding: 10px;

  border-style: solid;
  border-width: 1px;
  border-color: var(--bg-color);
  border-radius: 5px 5px 5px 5px;

  box-shadow: 2px 2px 1px black;
  box-sizing: border-box;

  -webkit-transition: border-color 0.5s;
  transition: border-color 0.5s;
}

.store-card:hover,
.store-card:focus-within {
  border-color: var(--front-color);
}

.store-card img {
  height: 150px;
  width: auto;
  justify-self: center;
}

.store-card h4 {
  justify-self: center;
}

.store-card p img {
  height: 15px;
}

.store-card p {
  justify-self: center;
}
</style>
