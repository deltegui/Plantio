<template>
<div class="store-panel" v-if="$store.logged">
  <div class="store-line">
      {{ $store.user.money }}
      <img src="credito.png"/>
  </div>
</div>
</template>

<script>
export default {
  name: 'StorePanel',
};
</script>

<style scoped>

.store-panel {
  position: absolute;
  left: 10px;
  top: 130px;

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

.store-line > img {
    height: 17px;
}

@media only screen and (max-width: 890px) {
  .store-panel {
    font-size: 15px;
  }

  .store-panel {
    display: block;
  }
}
</style>
