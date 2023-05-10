import * as base from "./baseService";
const model = "food";

export function getEmptyFood() {
  return {
    foodId: 0,
    foodName: "",
    foodCategory: "",
    foodDescription: "",
  };
}

export async function findAll() {
  return base.findAll(model);
}

export async function findById(foodId) {
  return base.findById(model, foodId);
}

export async function save(food) {
  return base.save(model, food, food.foodId);
}

export async function deleteById(foodId) {
  return base.deleteById(model, foodId);
}
