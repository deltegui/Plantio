const {post} = require('../methods');

const {
  hasher,
  jwt,
  userRepository,
} = require('./implementation');

const {
  LoginService,
} = require('./application');

const UserController = require('./user.controller');

const service = new LoginService(userRepository, hasher, jwt);
const controller = new UserController(service);

module.exports = [
  {
    bind: controller,
    handler: controller.login,
    endpoint: '/login',
    method: post,
  },
];

