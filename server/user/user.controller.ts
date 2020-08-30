import { oak } from "../deps.ts";
import { Controller } from "../controller.ts";
import { UserRepository } from "./gateways.ts";

export class UserController implements Controller {
  private readonly userRepo: UserRepository;

  constructor(userRepo: UserRepository) {
    this.userRepo = userRepo;
  }

  public async hello(ctx: oak.Context): Promise<void> {
    const user = await this.userRepo.getByName('diego');
    user.lastConnection = new Date();
    await this.userRepo.update(user);
    ctx.response.body = user;
  }

  public map(router: oak.Router): void {
    router.get("/", this.hello.bind(this));
  }
}