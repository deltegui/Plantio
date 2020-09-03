import { UserRepository, User, IUserService } from './gateways.ts';
import { ValidableFactory } from '../system/validator.ts';

export class UserService implements IUserService {
  constructor(
    private userRepo: UserRepository,
  ) {}

  async createUser(user: User): Promise<User> {
    return {
      name: '',
      password: '',
      lastConnection: new Date(),
    };
  }
}