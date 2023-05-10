package learn.recipes;

import learn.recipes.models.*;

import java.time.LocalDate;
import java.time.LocalTime;

public class TestHelper {

    static public AppUser makeAppUser(int id) {
        AppUser user = new AppUser();
        user.setAppUserId(id);
        user.setUsername("TestName");
        user.setPassword("Password");
        user.setEnabled(true);
        user.setFirstName("First");
        user.setLastName("Last");
        user.setEmail("TestEmail@email.com");
        user.setDob(LocalDate.of(1981, 10, 02));
        return user;
    }

    static public Food makeFood(int id) {
        Food food = new Food();
        food.setFoodId(id);
        food.setFoodName("Test Food Name");
        return food;
    }

    static public Tags makeTag(int id) {
        Tags tag = new Tags();
        tag.setTagId(id);
        tag.setTagName("Test Tag Name");
        tag.setDefaultImage("https://default-image-test.jpg");
        return tag;
    }

    static public Meal makeMeal(int id) {
        AppUser mealUser = makeAppUser(3);

        Meal meal = new Meal();
        meal.setMealId(id);
        meal.setAppUser(mealUser);
        meal.setDate(LocalDate.of(2023, 01, 16));
        meal.setTime(LocalTime.of(00, 00, 00));
        meal.setMealCategory("Test Meal Category");
        return meal;
    }

    static public Recipe makeRecipe(int id) {
        Recipe recipe = new Recipe();
        recipe.setRecipeId(id);
        recipe.setTitle("Test Recipe Title");
        recipe.setRecipeDescription("Test Recipe Description");
        recipe.setInstructions("Test Recipe Instructions");
        recipe.setCookTime(1);
        recipe.setPrepTime(1);
        recipe.setCalories(1);
        recipe.setServings(1);
        recipe.setImageUrl("https://image-url-test.jpg");
        return recipe;
    }


}