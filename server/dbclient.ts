import { mysql } from "./deps.ts";
import { config } from "./config.ts";

const client = await new mysql.Client();
client.connect(config.database);

export default client;