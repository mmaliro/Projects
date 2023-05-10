package learn.recipes.domain;

import jakarta.transaction.Transactional;
import learn.recipes.data.MealRepository;
import learn.recipes.models.Meal;
import learn.recipes.validation.Result;
import learn.recipes.validation.ResultType;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MealService {

    private final MealRepository mealRepository;

    public MealService(MealRepository mealRepository) {
        this.mealRepository = mealRepository;
    }

    public List<Meal> findAll() {
        return mealRepository.findAll();
    }

    public Meal findById(int mealId) {
        return mealRepository.findById(mealId).orElse(null);
    }

    public Result<Meal> save(Meal meal) {
        Result<Meal> result = validate(meal);
        if (!result.isSuccess()) {
            return result;
        }

        Meal payload = mealRepository.save(meal);
        result.setPayload(payload);
        return result;
    }

    @Transactional
    public boolean deleteById(int mealId) {
        if (mealId <= 0) {
            return false;
        }
        if (mealRepository.findById(mealId).isEmpty()) {
            return false;
        }
        mealRepository.deleteById(mealId);
        return true;
    }

    private Result<Meal> validate(Meal meal) {
        Result<Meal> result = new Result<>();

        if (meal == null) {
            result.addErr("", "meal cannot be null", ResultType.INVALID);
            return result;
        }

        if (meal.getMealId() > 0) {
            if (!mealRepository.existsById(meal.getMealId())) {
                result.addErr("", "not found", ResultType.NOT_FOUND);
                return result;
            }
        }

        if(meal.getDate() == null) {
            result.addErr("", "meal must have a date in yyyy-mm-dd form", ResultType.INVALID);
            return result;
        }

        if(meal.getTime() == null) {
            result.addErr("", "meal must have a timestamp in hh:mm:ss form", ResultType.INVALID);
            return result;
        }

        return result;
    }
}
