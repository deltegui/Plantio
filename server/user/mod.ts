import { UserController } from "./user.controller.ts";
import { PsqlUserRepository } from "./user.repo.ts";

const repo = new PsqlUserRepository();
const controller = new UserController(repo);

export default controller;