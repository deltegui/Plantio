import { oak } from "./deps.ts";

export interface Controller {
  map(app: oak.Router): void;
}