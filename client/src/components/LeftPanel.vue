<template>
<div id="text-console">
  <Console v-if="$store.logged"/>
  <Login @login="onLogin" v-else/>
</div>
</template>

<script>
import Console from './Console.vue';
import Login from './Login.vue';
import makeRequest from '../api/make_request';

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
    onLogin(user) {
      this.$actions.login(user);
      this.$actions.refreshWeather();
      makeRequest({
        method: 'GET',
        endpoint: '/hello',
        token: 'eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJuYW1lIjoiamF2aWVyIiwiZXhwaXJhdGlvbiI6IjIwMjAtMTEtMjFUMDI6MTg6MzkuNzcwMjc5NiIsImV4cCI6MTYwNTkxMzIwMH0.E3P_BS__1lQZOZywhSjpbkeJ54iD6UqNAAuPOsy6gH36DOVUlJEBkhHOp5UDPyBopjfmIeq3ojPaKUJb9fEWwA',
      }).then(console.log);
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
