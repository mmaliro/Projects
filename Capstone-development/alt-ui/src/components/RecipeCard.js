import { NavLink } from "react-router-dom";

export default function RecipeCard({ recipe }) {
  return (
    <NavLink to={`/recipes/${recipe.recipeId}`}>
      <div className="z-[-1] aspect-h-1 aspect-w-1 w-full overflow-hidden rounded-lg bg-gray-200 xl:aspect-h-8 xl:aspect-w-7 hover:scale-105 duration-300">
        {recipe.imageUrl && (
          <img
            src={recipe.imageUrl}
            className="h-full w-full object-cover object-center"
            alt={recipe.name}
          />
        )}
      </div>
      <h3 className="mt-4 text-md text-gray-700">{recipe.title}</h3>
    </NavLink>
  );
}
