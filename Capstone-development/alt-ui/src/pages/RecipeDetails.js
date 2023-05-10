import { useEffect, useState } from "react";
import { useNavigate, useParams, Link } from "react-router-dom";

import { findById } from "../services/recipeService";

function RecipeDetails() {
  const [recipe, setRecipe] = useState(null);

  const { recipeId } = useParams();
  const navigate = useNavigate();

  useEffect(() => {
    findById(recipeId).then(setRecipe);
  }, [recipeId]);

  if (!recipe) {
    return null;
  }

  return (
    <>
      <div className="w-full mx-8 py-16 px-4">
        <h1 className="flex justify-end md:text-4xl sm:text-3xl text-2xl font-bold py-2 pe-16 text-[#221c42]">
          {recipe.title}
        </h1>
        <div className="max-w-[1240px] mx-auto grid md:grid-cols-2">
          <img
            className="w-[500px] mx-auto my-4"
            src={recipe.imageUrl}
            alt="image of laptop"
          />
          <div className="flex mx-8 flex-col justify-center">
            <p className="text-[#6a8f6b] font-bold">
              Prep Time: {recipe.prepTime}
            </p>
            <p className="text-[#6a8f6b] font-bold">
              Cook Time: {recipe.cookTime}
            </p>

            <ul className="mt-6">
              <li className="p-4 border-b text-center border-gray-600">
                Ingredients List
              </li>
              {recipe.ingredients.map((i) => (
                <li className="p-4 border-b border-gray-600">
                  {i.amount} {i.measurementUnit} {i.food.foodName}
                </li>
              ))}
            </ul>
          </div>
        </div>
      </div>
      <div className="w-full flex mx-8 py-6 ps-8 bg-white ">
        <p className="leading-10">
          <pre className="font-sans ...">{recipe.instructions}</pre>
        </p>
      </div>
    </>
  );
}

export default RecipeDetails;
