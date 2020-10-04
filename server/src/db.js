const knex = require('knex');
const config = require('../knexfile');

let knexconfig = config.production;

if (process.env.NODE_ENV === 'test') {
  knexconfig = config.test;
}
if (process.env.NODE_ENV === 'development') {
  knexconfig = config.development;
}

if (process.env.NODE_ENV !== 'test') {
  console.log(`DB connection host: ${config.connection.host} ` +
    `user: ${config.connection.user} database: ${config.connection.database}`);
}

const instance = knex(knexconfig);

module.exports = instance;
