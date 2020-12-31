<template>
<div v-if="$store.logged">
  <StoreWindow v-if="showStoreWindow" @close="closeWindow"/>
  <div class="store-panel">
    <div class="store-line">
      <button class="field fynd-btn store-button" v-on:click="openWindow">
        <img class="store-icon" src="store-icon.png"/>
      </button>
      {{ $store.user.money }}
      <img class="credit-icon" src="credito.png"/>
    </div>
  </div>
</div>
</template>

<script>
import StoreWindow from './StoreWindow.vue';

export default {
  name: 'StorePanel',
  components: {
    StoreWindow,
  },
  data() {
    return {
      showStoreWindow: false,
    };
  },
  methods: {
    closeWindow() {
      this.showStoreWindow = false;
    },
    openWindow() {
      this.showStoreWindow = true;
    },
  },
};
</script>

<style scoped>

.store-panel {
  position: absolute;
  left: 10px;
  top: 150px;

  width: 300px;
  height: 150px;

  font-size: 20px;

  color: white;
}

.store-line {
  margin-bottom: 10px;
  width: 100%;
  vertical-align: middle;
}

.credit-icon {
  height: 17px;
}

.store-icon {
  height: 37px;
}

.store-button {
  display: inline;
  width: auto;
  height: auto;
  padding: 5px;
}

@media only screen and (max-width: 890px) {
  .store-panel {
    font-size: 15px;
  }

  .store-panel {
    display: block;
    top: 90px;
  }
}
</style>
