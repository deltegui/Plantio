<template>
<div id="text-console">
  <Console v-if="logged"/>
  <Login v-else/>
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
};
</script>

<style scoped>
@font-face {
  font-family: consoles;
  src: url(/console.ttf);
}

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

  float: left;
  overflow: scroll;

  font-family: consoles;
}
</style>
