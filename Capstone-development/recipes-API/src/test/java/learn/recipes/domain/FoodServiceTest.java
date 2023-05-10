package learn.recipes.domain;

import learn.recipes.TestHelper;
import learn.recipes.data.FoodRepository;
import learn.recipes.models.Food;
import learn.recipes.models.Tags;
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
public class FoodServiceTest {

    @Autowired
    FoodService service;

    @MockBean
    FoodRepository repository;

    @Test
    void shouldAddFood() {
        Food validNewFood = TestHelper.makeFood(0);

        when(repository.save(validNewFood)).thenReturn(validNewFood);
        Result<Food> result = service.save(validNewFood);

        assertTrue(result.isSuccess());
        assertEquals(ResultType.SUCCESS, result.getType());
    }

    @Test
    void shouldUpdateFood() {
        Food validUpdateFood = TestHelper.makeFood(1);

        when(repository.existsById(validUpdateFood.getFoodId())).thenReturn(true);
        when(repository.save(validUpdateFood)).thenReturn(validUpdateFood);
        Result<Food> result = service.save(validUpdateFood);

        assertTrue(result.isSuccess());
        assertEquals(ResultType.SUCCESS, result.getType());
    }

    @Test
    void shouldDeleteFood() {
        Food food = TestHelper.makeFood(2);
        when(repository.findById(food.getFoodId())).thenReturn(Optional.of(food));
        assertTrue(service.deleteById(food.getFoodId()));
    }

    @Test
    void shouldNotDeleteFoodWithInvalidId() {
        assertFalse(service.deleteById(0));
    }

    @Test
    void shouldNotDeleteMissingFood() {
        Food missingFood = TestHelper.makeFood(15);
        when(repository.findById(missingFood.getFoodId())).thenReturn(Optional.empty());

        assertFalse(service.deleteById(missingFood.getFoodId()));
    }

    @Test
    void shouldNotSaveNullFood() {
        Result<Food> result = service.save(null);

        assertFalse(result.isSuccess());
        assertEquals("food cannot be null", result.getErrs().get(0).getMessage());
    }

    @Test
    void shouldNotUpdateMissingFood() {
        Food missingFood = TestHelper.makeFood(15);
        when(repository.existsById(missingFood.getFoodId())).thenReturn(false);

        Result<Food> result = service.save(missingFood);

        assertFalse(result.isSuccess());
        assertEquals("not found", result.getErrs().get(0).getMessage());
    }

    @Test
    void shouldNotSaveFoodWithBlankName() {
        Food blankNameFood = TestHelper.makeFood(0);
        blankNameFood.setFoodName("  ");

        Result<Food> result = service.save(blankNameFood);

        assertFalse(result.isSuccess());
        assertEquals("food name is required", result.getErrs().get(0).getMessage());
    }

    @Test
    void shouldNotSaveFoodWithNullName() {
        Food nullNameFood = TestHelper.makeFood(0);
        nullNameFood.setFoodName(null);

        Result<Food> result = service.save(nullNameFood);

        assertFalse(result.isSuccess());
        assertEquals("food name is required", result.getErrs().get(0).getMessage());
    }

    @Test
    void shouldNotSaveFoodWithExistingName() {
        Food existingNameFood = TestHelper.makeFood(0);
        existingNameFood.setFoodName("existing name");
        when(repository.findByFoodName(existingNameFood.getFoodName())).thenReturn(existingNameFood);

        Result<Food> result = service.save(existingNameFood);

        assertFalse(result.isSuccess());
        assertEquals("food name must be unique", result.getErrs().get(0).getMessage());
    }

}
