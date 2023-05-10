package learn.recipes.domain;

import learn.recipes.data.AppUserRepository;
import learn.recipes.data.FoodRepository;
import learn.recipes.models.AppUser;
import learn.recipes.models.Food;
import learn.recipes.validation.Result;
import learn.recipes.validation.ResultType;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FoodService {

    private final FoodRepository foodRepository;

    public FoodService(FoodRepository foodRepository) {
        this.foodRepository = foodRepository;
    }

    public List<Food> findAll() {return foodRepository.findAll(); }

    public Food findById(int foodId) {return foodRepository.findById(foodId).orElse(null);}

    public Result<Food> save(Food food) {
        Result<Food> result = validate(food);
        if(!result.isSuccess()) {
            return result;
        }

        Food payload = foodRepository.save(food);
        result.setPayload(payload);
        return result;

    }

    public boolean deleteById(int foodId) {
        if (foodId <= 0) {
            return false;
        }
        if (foodRepository.findById(foodId).isEmpty()) {
            return false;
        }

        foodRepository.deleteById(foodId);
        return true;
    }

    private Result<Food> validate(Food food) {
        Result<Food> result = new Result<>();

        if (food == null) {
            result.addErr("", "food cannot be null", ResultType.INVALID);
            return result;
        }

        if (food.getFoodId() > 0) {
            if (!foodRepository.existsById(food.getFoodId())) {
                result.addErr("", "not found", ResultType.NOT_FOUND);
                return result;
            }
        }

        if(Validations.isNullOrBlank(food.getFoodName())) {
            result.addErr("", "food name is required", ResultType.INVALID);
        }
        if(foodRepository.findByFoodName(food.getFoodName()) != null) {
            result.addErr("", "food name must be unique", ResultType.ALREADY_EXISTS);
        }
        return result;
    }
}