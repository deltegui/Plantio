import { fs, log } from "./deps.ts";

export type Configuration = {
  url: string,
  database: {
    host: string,
    username: string,
    password: string,
    db: string,
  },
};

export const config: Configuration = await fs.readJson("./config.json")
    .then(config => <Configuration> config)
    .catch(err => {
      log.error(err);
      Deno.exit(1);
    });