import { log } from '../deps.ts';
import { UserRepository, User } from "./gateways.ts";
import client from "../dbclient.ts";

const rawToUser = (data: [string, string, Date]) => ({
  name: data[0],
  password: data[1],
  lastConnection: data[2],
});

const gmtMinutesOffsetToUTC = (offset: number): string => {
  const sign = (offset < 0) ? '+': '-';
  return sign + formatNumber(Math.abs(offset/60)) + ':' + formatNumber(Math.abs(offset%60));
}

const formatNumber = (n: number) => ("0" + n).slice(-2);

const dateToRaw = (d: Date): string =>
  `${d.getUTCFullYear()}-${formatNumber(d.getUTCMonth()+1)}-${formatNumber(d.getUTCDate())} ` +
  `${formatNumber(d.getUTCHours())}:${formatNumber(d.getUTCMinutes())}:${formatNumber(d.getUTCSeconds())}.` +
  `${formatNumber(d.getMilliseconds())} ${gmtMinutesOffsetToUTC(d.getTimezoneOffset())}`;

export class PsqlUserRepository implements UserRepository {
  async exists(name: string): Promise<boolean> {
    return this.getByName(name)
      .then(() => true)
      .catch(() => false);
  }

  async save(user: User): Promise<User> {
    return await this.runQuery(user, "insert into users (name, password, last_connection) values ($1, $2, $3)");
  }

  async update(user: User): Promise<User> {
    return await this.runQuery(user, "update users set password=$2, last_connection=$3 where name=$1");
  }

  private async runQuery({ name, password, lastConnection }: User, query: string): Promise<User> {
    try {
      const formattedDate = dateToRaw(lastConnection);
      await client.query({
        text: query,
        args: [name, password, formattedDate],
      });
    } catch(err) {
      log.error(err);
    }
    return { name, password, lastConnection };
  }

  async getByName(name: string): Promise<User> {
    const result = await client.query("select name, password, last_connection from users");
    if(!result || result.rowCount == undefined || result.rowCount <= 0) {
      return Promise.reject();
    }
    return rawToUser(result.rows[0]);
  }
}