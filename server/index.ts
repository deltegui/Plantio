import { oak, log } from "./deps.ts";
import { config } from "./config.ts";
import createApi from "./api.ts";

const app = new oak.Application();

app.use((ctx, next) => {
  const request = ctx.request;
  log.info(`[${request.method}] ${request.url}`);
  next();
});

createApi(app);

log.info(`Listening on ${config.url}`);
await app.listen(config.url);