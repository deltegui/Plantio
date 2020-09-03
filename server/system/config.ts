import { fs, log } from "../deps.ts";

export type Configuration = {
  url: string,
  database: {
    hostname: string,
    user: string,
    password: string,
    database: string,
    port: number,
  },
};

export const config: Configuration = await fs.readJson("./config.json")
    .then(config => <Configuration> config)
    .catch(err => {
      log.error(err);
      Deno.exit(1);
    });