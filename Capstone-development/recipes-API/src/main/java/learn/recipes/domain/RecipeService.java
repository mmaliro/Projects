package learn.recipes.domain;

import learn.recipes.data.RecipeRepository;
import learn.recipes.models.Recipe;
import learn.recipes.validation.Result;
import learn.recipes.validation.ResultType;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecipeService {
    private final RecipeRepository recipeRepository;

    public RecipeService(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    public List<Recipe> findAll() {return recipeRepository.findAll(); }

    public Recipe findById(int recipeId) {return recipeRepository.findById(recipeId).orElse(null);}

    public Result<Recipe> save(Recipe recipe) {
        Result<Recipe> result = validate(recipe);
        if(!result.isSuccess()) {
            return result;
        }

        Recipe payload = recipeRepository.save(recipe);
        result.setPayload(payload);
        return result;

    }

    public boolean deleteById(int recipeId) {
        if (recipeId <= 0) {
            return false;
        }
        if (recipeRepository.findById(recipeId).isEmpty()) {
            return false;
        }

        recipeRepository.deleteById(recipeId);
        return true;
    }

    private Result<Recipe> validate(Recipe recipe) {
        Result<Recipe> result = new Result<>();

        if (recipe == null) {
            result.addErr("", "recipe cannot be null", ResultType.INVALID);
            return result;
        }
        if (recipe.getRecipeId() > 0) {
            if (!recipeRepository.existsById(recipe.getRecipeId())) {
                result.addErr("", "not found", ResultType.NOT_FOUND);
                return result;
            }
        }
        if(Validations.isNullOrBlank(recipe.getTitle())) {
            result.addErr("", "recipe title is required", ResultType.INVALID);
        }
        if(Validations.isNullOrBlank(recipe.getInstructions())) {
            result.addErr("", "recipe instructions are required", ResultType.INVALID);
        }
        if(recipe.getCookTime() < 0) {
            result.addErr("", "cook time must be at least 0", ResultType.INVALID);
        }
        if(recipe.getPrepTime() < 0) {
            result.addErr("", "prep time must be at least 0", ResultType.INVALID);
        }
        //TODO Delete?
//        if(recipe.getCalories() <= 0) {
//            result.addErr("", "calories must be greater than 0", ResultType.INVALID);
//        }
        if(recipe.getServings() <= 0) {
            result.addErr("", "servings must be greater than 0", ResultType.INVALID);
        }
        return result;
    }

}