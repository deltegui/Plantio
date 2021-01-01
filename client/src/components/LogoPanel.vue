<template>
  <span class="logo-panel" v-if="notLogged">
    <img src="rose_logo.png"/>
    <h1>Plantio</h1>
  </span>
</template>

<script>
export default {
  name: 'LogoPanel',
  computed: {
    notLogged() {
      return !this.$store.logged;
    },
  },
};
</script>

<style scoped>
.logo-panel {
  position: absolute;
  top: 10px;
  left: 20px;

  text-align: center;
  vertical-align: middle;
}

.logo-panel * {
  float:left;
  box-sizing: border-box;
}

.logo-panel h1 {
  color: green;
  font-size: 40px;
  margin-top: 60px;
  margin-left: 20px;
}

@media only screen and (max-width: 890px) {
  .logo-panel img {
    height: 50px;
  }

  .logo-panel h1 {
    font-size: 16px;
    margin-top: 11px;
    margin-left: 10px;
  }
}
</style>
