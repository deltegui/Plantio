import { UserController } from './user.controller.ts';
import { UserService } from './user.service.ts';
import { UserValidationCases, UserServiceValidator } from './user.validation.ts';
import { PsqlUserRepository } from './user.repo.ts';
import { UserRepository } from './gateways.ts';
import { JsonValidatorFactory, ValidableFactory } from '../system/validator.ts';

const factory: ValidableFactory = new JsonValidatorFactory()
  .register((t: any) => ({
    name: t.string.required,
    password: t.string.required,
  }), UserValidationCases.Registration);

const repo = new PsqlUserRepository();
const service = new UserService(repo);
const validatorService = new UserServiceValidator(factory, service);
const controller = new UserController(validatorService);

export default controller;