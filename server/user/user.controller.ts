import { oak } from '../deps.ts';
import { Controller } from '../system/controller.ts';
import { User } from './gateways.ts';
import { UserService } from './user.service.ts';

export class UserController implements Controller {
  constructor(private userService: UserService) {}

  public async register(ctx: oak.Context): Promise<void> {
    const body = ctx.request.body()
    if(body.type !== 'json') {

    }
    const user = await body.value as User;
    ctx.response.body = this.userService.createUser(user);
  }

  public map(router: oak.Router): void {
    router.get("/", this.register.bind(this));
  }
}