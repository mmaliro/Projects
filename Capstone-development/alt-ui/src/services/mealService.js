import * as base from "./baseService";
const model = "meal";

export function getEmptyMeal() {
  return {
    mealId: 0,
    date: "",
    time: "",
    mealCategory: "",
  };
}

export async function findAll() {
  return base.findAll(model);
}

export async function findById(mealId) {
  return base.findById(model, mealId);
}

export async function save(meal) {
  return base.save(model, meal, meal.mealId);
}

export async function deleteById(mealId) {
  return base.deleteById(model, mealId);
}
