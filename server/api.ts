import { oak } from "./deps.ts";
import { Controller } from "./system/controller.ts";
import userController from "./user/mod.ts";

const controllers: Controller[] = [
  userController,
];

export default (app: oak.Application) =>
  controllers.forEach(controller => {
    const router = new oak.Router();
    controller.map(router);
    app.use(router.routes());
  });