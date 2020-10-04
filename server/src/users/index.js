const {post} = require('../methods');

const {
  hasher,
  jwt,
  userRepository,
} = require('./implementation');

const {
  LoginService,
  RegisterService,
} = require('./application');

const UserController = require('./user.controller');

const loginService = new LoginService(userRepository, hasher, jwt);
const registerService = new RegisterService(userRepository, hasher, jwt);
const controller = new UserController(loginService, registerService);

module.exports = [
  {
    bind: controller,
    handler: controller.login,
    endpoint: '/login',
    method: post,
  },
  {
    bind: controller,
    handler: controller.register,
    endpoint: '/',
    method: post,
  },
];

