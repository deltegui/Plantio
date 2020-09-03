import { oak, log } from "./deps.ts";
import { config } from "./system/config.ts";
import createApi from "./api.ts";

const app = new oak.Application();

app.use(async (ctx, next) => {
  const request = ctx.request;
  log.info(`[${request.method}] ${request.url}`);
  await next();
});

createApi(app);

log.info(`Listening on ${config.url}`);
await app.listen(config.url);