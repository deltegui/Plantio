const {post} = require('../methods');

const {
  hasher,
  jwt,
  userRepository,
  tokenRepository,
} = require('./implementation');

const {
  LoginService,
  RegisterService,
} = require('./application');

const UserController = require('./user.controller');

const loginService = new LoginService(
    userRepository,
    tokenRepository,
    hasher,
    jwt,
);
const registerService = new RegisterService(
    userRepository,
    tokenRepository,
    hasher,
    jwt,
);
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

