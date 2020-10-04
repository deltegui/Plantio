const app = require('./app');

console.log(`Environment: ${process.env.NODE_ENV}`);

app.listen(process.env.LISTEN_URL, () => {
  console.log(`Listening on ${process.env.LISTEN_URL}`);
});
