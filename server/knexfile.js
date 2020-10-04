require('dotenv').config();
const path = require('path');

const env = process.env;
const migrations = {
  directory: path.join(__dirname, 'migrations'),
};

module.exports = {
  production: {
    client: 'mysql',
    connection: {
      host: env.MYSQL_URL,
      user: env.MYSQL_USER,
      password: env.MYSQL_PASSWORD,
      database: env.MYSQL_DB,
    },
    migrations,
  },

  development: {
    client: 'mysql',
    connection: {
      host: '127.0.0.1',
      user: 'root',
      password: 'root',
      database: 'plantio',
    },
    migrations,
  },

  test: {
    client: 'sqlite3',
    connection: ':memory:',
    migrations,
  },
};
