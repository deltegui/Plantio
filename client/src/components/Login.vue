<template>
  <div class="login">
    <label for="username">Usuario</label>
    <input v-model="username" id="username" class="field" type="text"/>
    <label for="password">Password</label>
    <input v-model="password" id="password" class="field" type="password"/>
    <button v-on:click="doLogin" class="field fynd-btn">Login</button>
    <button class="field fynd-btn">Register</button>
  </div>
</template>

<script>
// import store from '../store';

export default {
  name: 'Login',
  data() {
    return {
      username: "",
      password: "",
    };
  },
  methods: {
    doLogin() {
    },
  },
};
</script>

<style scoped>
label {
  display: block;
  margin-bottom: 10px;
}

.login {
  width: 90%;
  margin-left: auto;
  margin-right: auto;
  padding-top: 40px;

  --bg-color: #353535;
  --front-color: #00d1b2;
  --danger-color: #b31d12;
}

input {
  outline: none;
  -webkit-box-flex: 18;
  flex-grow: 18;
  border-width: 0px;
  font-size: 20px;

  padding-left: 7px;
  background-color: var(--bg-color);
  color: white;
  border-color: var(--bg-color);
}

.field {
  width: 100%;
  height: 40px;
  margin-bottom: 10px;
  overflow: hidden;

  border-style: solid;
  border-width: 1px;
  border-color: var(--bg-color);
  border-radius: 5px 5px 5px 5px;

  box-shadow: 2px 2px 1px black;
  box-sizing: border-box;

  display: flex;

  font-size: 14px;
  font-family: consoles;

  -webkit-transition: border-color 0.5s;
  transition: border-color 0.5s;
}

.field:hover,
.field:focus-within {
  border-color: var(--front-color);
}

.fynd-btn {
  padding: 10px;

  border-style: solid;
  border-radius: 10px 10px 10px 10px;
  border-width: 1px;
  border-color: var(--front-color);

  background-color: #252525;
  color: white;

  text-align: center;
  font-size: 14px;
  font-family: consoles;

  -webkit-transition: background-color 0.5s;
  transition: background-color 0.5s;
}

.fynd-btn:hover {
  background-color: var(--front-color);
}

.danger-btn {
  border-color: var(--danger-color);
}

.danger-btn:hover {
  background-color: var(--danger-color);
}
</style>
