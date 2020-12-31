<template>
  <div id="store-window">
    <div id="header">
      <button class="field fynd-btn close-btn" v-on:click="handleClose">
        <img src="/close-icon.png"/>
      </button>
      <h2>Buy seeds</h2>
      <span>
        {{$store.user.money}}
        <img src="credito.png"/>
      </span>
    </div>
    <div id="cards">
      <span :key=name v-for="{ amount, price, name } in storeItems">
        <StoreCard :max=amount :price=price :seedsType=name />
      </span>
    </div>
  </div>
</template>

<script>
import StoreCard from './StoreCard.vue';
import storeService from '../logic/store.service';

export default {
  name: 'StoreWindow',
  data() {
    return {
      storeItems: [],
    };
  },
  components: {
    StoreCard,
  },
  mounted() {
    storeService.getAll().then((items) => {
      this.storeItems = items;
    });
  },
  methods: {
    handleClose() {
      this.$emit('close');
    },
  },
};
</script>

<style scoped>
#header {
  position: fixed;
  width: 70vw;
  height: 50px;
}

#header img {
  height: 20px;
}

#header h2 {
  margin: 25px 0px 0px 10px;
  padding: 0px;
  display: inline;
  width: auto;
}

#header span {
  margin-left: 40px;
  font-size: 25px;
}

.close-btn {
  height: auto;
  width: auto;
  float: left;
}

#cards {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
  align-items: center;
  gap: 50px;
  padding-top: 50px;
}

#store-window {
  box-sizing: border-box;
  z-index: 9999;
  overflow: scroll;

  --margin: 110px;
  position: absolute;
  top: var(--margin);
  bottom: var(--margin);
  left: var(--margin);
  right: var(--margin);
  height: auto;

  background-color: #252525;
  color: white;
  border-radius: 10px;
  box-shadow: #404040 0px 0px 20px;
  padding: 20px;
}

.btn-spin {
  display: inline;
  width: 49px;
  margin-left: 30px;
  padding-left: 5px;
}

@media only screen and (max-width: 890px) {
  #store-window {
    --margin: 0px;
  }

  #header {
    width: 100%;
  }

  #header span {
    margin-left: auto;
    margin-right: 50px;
    float: right;
  }
}
</style>
