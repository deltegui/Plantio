const {
  post,
} = require('../methods');
const UserController = require('./user.controller.js');

const controller = new UserController();

module.exports = [
  {
    bind: controller,
    handler: controller.login,
    endpoint: '/login',
    method: post,
  },
];

