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
import ButtonSpin from './ButtonSpin.vue';
import userService from '../logic/user.service';

function formatErrors(errorObj) {
  const equivalence = {
    password: 'Password',
    name: 'Usuario',
  };
  return Object.keys(errorObj)
    .map((key) => `${equivalence[key]}: ${errorObj[key]}`);
}

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
        .catch((err) => {
          if (err.errors) {
            this.errors = formatErrors(err.errors);
          } else {
            this.errors = [err.message];
          }
        })
        .then(done);
    },

    login(done) {
      this.handleResopnse(userService.login({
        user: this.username,
        password: this.password,
      }), done);
    },

    register(done) {
      this.handleResopnse(userService.register({
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
</style>
