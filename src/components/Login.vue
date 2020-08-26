<template>
  <div class="login">
    <span>Usuario</span>
    <input class="field" type="text"/>
    <span>Password</span>
    <input class="field" type="password"/>
    <button class="field fynd-btn">Login</button>
    <button class="field fynd-btn">Logout</button>
  </div>
</template>

<script>
export default {
  name: 'Login',
};
</script>

<style scoped>
span {
  display: block;
  margin-bottom: 10px;
}

.login {
  width: 90%;
  margin-left: auto;
  margin-right: auto;
  padding-top: 40px;
}

input {
  outline: none;
  -webkit-box-flex: 18;
          flex-grow: 18;
  border-width: 0px;
  font-size: 20px;
  padding-left: 7px;
  background-color: #353535;
  color: white;
  border-color: #353535;
}

.field {
  width: 100%;
  display: -webkit-box;
  display: flex;
  height: 40px;
  margin-bottom: 10px;
  overflow: hidden;
  border-style: solid;
  border-width: 1px;
  border-color: #353535;
  box-shadow: 2px 2px 1px black;
  border-radius: 5px 5px 5px 5px;
  -webkit-transition: border-color 0.5s;
  transition: border-color 0.5s;
}

.field:hover,
.field:focus-within {
  border-color: #00d1b2;
}

.fynd-btn {
  text-align: center;
  padding: 10px;
  border-style: solid;
  border-radius: 10px 10px 10px 10px;
  border-width: 1px;
  border-color: #00d1b2;
  background-color: #252525;
  color: white;
  font-size: 16px;
  -webkit-transition: background-color 0.5s;
  transition: background-color 0.5s;
}

.fynd-btn:hover {
  background-color: #00d1b2;
}

.danger-btn {
  border-color: #b31d12;
}

.danger-btn:hover {
  background-color: #b31d12;
}
</style>
