import { psql } from "./deps.ts";
import { config } from "./config.ts";

const client = new psql.Client(config.database);
await client.connect();

export default client;