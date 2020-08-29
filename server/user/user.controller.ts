import { oak } from "../deps.ts";
import { Controller } from "../controller.ts";

export class UserController implements Controller {
  public hello(ctx: oak.Context): void {
    ctx.response.body = "Hello users!";
  }

  public map(router: oak.Router): void {
    router.get("/", this.hello.bind(this));
  }
}