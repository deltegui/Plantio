<template>
  <div class="login">
    <label for="username">Usuario</label>
    <input v-model="username" id="username" class="field" type="text"/>
    <label for="password">Password</label>
    <input v-model="password" id="password" class="field" type="password"/>
    <button-spin @click="login" text="Login"/>
    <button-spin @click="register" text="Register"/>
    <div class="field danger-field" v-for="(error, index) in errors" v-bind:key="index">
      {{error}}
    </div>
  </div>
</template>

<script>
import api from '../api';
import ButtonSpin from './ButtonSpin.vue';

export default {
  name: 'Login',
  components: {
    ButtonSpin,
  },
  data() {
    return {
      username: "",
      password: "",
      errors: [],
    };
  },
  methods: {
    handleResopnse(res, done) {
      res
        .then((user) => this.$emit('login', user))
        .catch((err) => {
          if (err.errors) {
            this.errors = err.errors;
          } else {
            this.errors = [err.title];
          }
        })
        .then(done);
    },

    login(done) {
      this.handleResopnse(api.user.login({
        user: this.username,
        password: this.password,
      }), done);
    },

    register(done) {
      this.handleResopnse(api.user.register({
        user: this.username,
        password: this.password,
      }), done);
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
  min-height: 40px;
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

.danger-field {
  padding: 10px;
  height: 100%;
  border-color: var(--danger-color);
  background-color: var(--danger-color);
}

.danger-field:hover {
  border-color: var(--danger-color);
  background-color: var(--danger-color);
}
</style>
