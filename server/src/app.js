require('dotenv').config();
const express = require('express');
const mappings = require('./mappings');

function addToRouter(router, {bind, handler, method, endpoint}) {
  router[method](endpoint, handler.bind(bind));
  return router;
}

function mapEndpoints(app, mappings) {
  mappings.forEach(({mountpoint, routes}) => {
    const router = routes.reduce(addToRouter, new express.Router());
    app.use(mountpoint, router);
  });
}

const app = express();
app.use(express.json());
mapEndpoints(app, mappings);

module.exports = app;
