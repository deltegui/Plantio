import { userNotFound } from './errors.ts';
import { ValidableFactory, ValidationDecorator } from '../system/validator.ts';
import { UserService } from './user.service.ts';
import { User } from './gateways.ts';

export enum UserValidationCases {
  Registration,
};

export class UserServiceValidator extends ValidationDecorator {
  constructor(
    validableFactory: ValidableFactory,
    private userService: UserService,
  ) {
    super(validableFactory);
  }

  async createUser(user: User): Promise<User> {
    this.throwIfInvalid({
      model: user,
      validatorID: UserValidationCases.Registration,
      error: userNotFound,
    });
    return this.userService.createUser(user);
  }
}