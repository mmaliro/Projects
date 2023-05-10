package learn.recipes.domain;

import learn.recipes.TestHelper;
import learn.recipes.data.MealRepository;
import learn.recipes.models.Meal;
import learn.recipes.validation.Result;
import learn.recipes.validation.ResultType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class MealServiceTest {

    @Autowired
    MealService service;

    @MockBean
    MealRepository repository;

    @Test
    void shouldAddMeal() {
        Meal validNewMeal = TestHelper.makeMeal(0);

        when(repository.save(validNewMeal)).thenReturn(validNewMeal);
        Result<Meal> result = service.save(validNewMeal);

        assertTrue(result.isSuccess());
        assertEquals(validNewMeal, result.getPayload());
    }

    @Test
    void shouldUpdateMeal() {
        Meal validUpdateMeal = TestHelper.makeMeal(1);

        when(repository.existsById(validUpdateMeal.getMealId())).thenReturn(true);
        when(repository.save(validUpdateMeal)).thenReturn(validUpdateMeal);
        Result<Meal> result = service.save(validUpdateMeal);

        assertTrue(result.isSuccess());
        assertEquals(validUpdateMeal, result.getPayload());
    }

    @Test
    void shouldDeleteMeal() {
        Meal meal = TestHelper.makeMeal(2);
        when(repository.findById(meal.getMealId())).thenReturn(Optional.of(meal));

        assertTrue(service.deleteById(2));
    }

    @Test
    void shouldNotDeleteMealWithInvalidId() {
        assertFalse(service.deleteById(0));
    }

    @Test
    void shouldNotDeleteMissingMeal() {
        Meal missingMeal = TestHelper.makeMeal(7);
        when(repository.findById(missingMeal.getMealId())).thenReturn(Optional.empty());

        assertFalse(service.deleteById(7));
    }

    @Test
    void shouldNotSaveNullMeal() {
        Result<Meal> result = service.save(null);

        assertFalse(result.isSuccess());
        assertEquals("meal cannot be null", result.getErrs().get(0).getMessage());
    }

    @Test
    void shouldNotUpdateMissingMeal() {
        Meal missingMeal = TestHelper.makeMeal(7);
        when(repository.existsById(missingMeal.getMealId())).thenReturn(false);

        Result<Meal> result = service.save(missingMeal);

        assertFalse(result.isSuccess());
        assertEquals("not found", result.getErrs().get(0).getMessage());
    }

    @Test
    void shouldNotSaveMealWithNoDate() {
        Meal noDateMeal = TestHelper.makeMeal(0);
        noDateMeal.setDate(null);

        Result<Meal> result = service.save(noDateMeal);

        assertFalse(result.isSuccess());
        assertEquals("meal must have a date in yyyy-mm-dd form", result.getErrs().get(0).getMessage());
    }

    @Test
    void shouldNotSaveMealWithNoTime() {
        Meal noTimeMeal = TestHelper.makeMeal(0);
        noTimeMeal.setTime(null);

        Result<Meal> result = service.save(noTimeMeal);

        assertFalse(result.isSuccess());
        assertEquals("meal must have a timestamp in hh:mm:ss form", result.getErrs().get(0).getMessage());
    }

}
