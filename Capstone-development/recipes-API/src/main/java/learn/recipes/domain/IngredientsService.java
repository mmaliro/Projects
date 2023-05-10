package learn.recipes.domain;

import jakarta.transaction.Transactional;
import learn.recipes.data.IngredientsRepository;
import learn.recipes.data.RecipeRepository;
import learn.recipes.models.Ingredients;
import learn.recipes.models.Recipe;
import learn.recipes.validation.Result;
import learn.recipes.validation.ResultType;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IngredientsService {

    private final IngredientsRepository ingredientsRepository;

    private final RecipeRepository recipeRepository;

    public IngredientsService(IngredientsRepository ingredientsRepository, RecipeRepository recipeRepository) {
        this.ingredientsRepository = ingredientsRepository;
        this.recipeRepository = recipeRepository;
    }

    public List<Ingredients> findAll() {
        return ingredientsRepository.findAll();
    }

    public Ingredients findById(int mealId) {
        return ingredientsRepository.findById(mealId).orElse(null);
    }

    public Result<Ingredients> save(Ingredients ingredient) {
        Result<Ingredients> result = validate(ingredient);
        if (!result.isSuccess()) {
            return result;
        }

        Ingredients payload = ingredientsRepository.save(ingredient);
        result.setPayload(payload);
        return result;
    }

    @Transactional
    public boolean deleteById(int ingredientId) {
        if (ingredientId <= 0) {
            return false;
        }
        if (ingredientsRepository.findById(ingredientId).isEmpty()) {
            return false;
        }
        ingredientsRepository.deleteById(ingredientId);
        return true;
    }

    private Result<Ingredients> validate(Ingredients ingredient) {
        Result<Ingredients> result = new Result<>();

        if (ingredient == null) {
            result.addErr("", "ingredient cannot be null", ResultType.INVALID);
            return result;
        }

        if (ingredient.getIngredientId() > 0) {
            if (!ingredientsRepository.existsById(ingredient.getIngredientId())) {
                result.addErr("", "not found", ResultType.NOT_FOUND);
                return result;
            }
        }

        if(ingredient.getAmount() <= 0) {
            result.addErr("", "ingredient must have an amount greater than zero (0)", ResultType.INVALID);
            return result;
        }

        return result;
    }
}

