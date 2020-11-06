<template>
<div id="text-console">
  <Console v-if="logged"/>
  <Login :onLogin="onLogin.bind(this)" v-else/>
</div>
</template>

<script>
import Console from './Console.vue';
import Login from './Login.vue';

export default {
  name: 'LeftPanel',
  data: () => ({
    logged: false,
  }),
  components: {
    Console,
    Login,
  },
  methods: {
    onLogin() {
      console.log("loging");
      this.logged = true;
    },
  },
};
</script>

<style scoped>
#text-console {
  box-sizing: border-box;

  background-color: #272727;
  color: #E5E5E5;

  width: 430px;
  min-width: 430px;
  height: 90vh;
  margin-top: 5vh;
  margin-right: 5px;
  padding: 5px;
  border-radius: 10px;
}

@media only screen and (max-width: 890px) {
  body {
    overflow: scroll;
  }

  #text-console {
    width: 100%;
    min-width: 100%;
    max-height: 360px;
    height: 360px;
    position: absolute;
    bottom: 0px;
  }
}
</style>
