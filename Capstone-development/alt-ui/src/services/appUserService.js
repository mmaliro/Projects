import * as base from "./baseService";
const model = "appUser";

export function getEmptyUser() {
  return {
    AppUserId: 0,
    userName: "",
    password: "",
    enabled: false,
    firstName: "",
    lastName: "",
    email: "",
    dob: "",
    roles: [],
    recipes: [],
  };
}

export async function findAll() {
  return base.findAll(model);
}

export async function findById(appUserId) {
  return base.findById(model, appUserId);
}

export async function save(appUser) {
  return base.save(model, appUser, appUser.appUserId);
}

export async function deleteById(appUserId) {
  return base.deleteById(model, appUserId);
}
