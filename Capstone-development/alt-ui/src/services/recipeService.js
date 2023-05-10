import * as base from "./baseService";
const model = "recipe";

export function getEmptyRecipe() {
  return {
    recipeId: 0,
    title: "",
    instructions: "",
    recipeDescription: "",
    cookTime: "",
    prepTime: "",
    calories: "",
    servings: "",
    imageUrl: "",
    tags: [],
    foods: [],
    ingrediants: [],
  };
}

export async function findAll() {
  return base.findAll(model);
}

export async function findById(recipeId) {
  return base.findById(model, recipeId);
}

export async function save(recipe) {
  return base.save(model, recipe, recipe.recipeId);
}

export async function deleteById(recipeId) {
  return base.deleteById(model, recipeId);
}
