const knex = require('knex');

const env = process.env;
const connection = {
  host: env.MYSQL_URL,
  user: env.MYSQL_USER,
  password: env.MYSQL_PASSWORD,
  database: env.MYSQL_DB,
};
console.log(`DB connection host: ${connection.host} ` +
  `user: ${connection.user} database: ${connection.database}`);
const instance = knex({
  client: 'mysql',
  connection,
});

module.exports = instance;
