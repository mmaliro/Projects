import * as base from "./baseService";

export async function findAll() {
  return base.findAll("appRole");
}
