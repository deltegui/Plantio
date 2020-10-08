const {post} = require('../methods');

const {
  hasher,
  tokenizer,
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
    tokenizer,
);
const registerService = new RegisterService(
    userRepository,
    tokenRepository,
    hasher,
    tokenizer,
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

