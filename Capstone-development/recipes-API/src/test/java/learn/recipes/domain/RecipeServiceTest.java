package learn.recipes.domain;

import learn.recipes.TestHelper;
import learn.recipes.data.RecipeRepository;
import learn.recipes.models.Recipe;
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
public class RecipeServiceTest {

    @Autowired
    RecipeService service;

    @MockBean
    RecipeRepository repository;

    @Test
    void shouldAddRecipe() {
        Recipe validNewRecipe = TestHelper.makeRecipe(0);

        when(repository.save(validNewRecipe)).thenReturn(validNewRecipe);
        Result<Recipe> result = service.save(validNewRecipe);

        assertTrue(result.isSuccess());
        assertEquals(ResultType.SUCCESS, result.getType());
    }

    @Test
    void shouldUpdateRecipe() {
        Recipe validUpdateRecipe = TestHelper.makeRecipe(1);

        when(repository.existsById(validUpdateRecipe.getRecipeId())).thenReturn(true);
        when(repository.save(validUpdateRecipe)).thenReturn(validUpdateRecipe);
        Result<Recipe> result = service.save(validUpdateRecipe);

        assertTrue(result.isSuccess());
        assertEquals(ResultType.SUCCESS, result.getType());
    }

    @Test
    void shouldDeleteRecipe() {
        Recipe recipe = TestHelper.makeRecipe(2);
        when(repository.findById(recipe.getRecipeId())).thenReturn(Optional.of(recipe));
        assertTrue(service.deleteById(2));
    }

    @Test
    void shouldNotDeleteRecipeWithInvalidId() {
        assertFalse(service.deleteById(0));
    }

    @Test
    void shouldNotDeleteMissingRecipe() {
        Recipe missingRecipe = TestHelper.makeRecipe(7);
        when(repository.findById(missingRecipe.getRecipeId())).thenReturn(Optional.empty());

        assertFalse(service.deleteById(missingRecipe.getRecipeId()));
    }

    @Test
    void shouldNotSaveNullRecipe() {
        Result<Recipe> result = service.save(null);

        assertFalse(result.isSuccess());
        assertEquals("recipe cannot be null", result.getErrs().get(0).getMessage());
    }

    @Test
    void shouldNotUpdateMissingRecipe() {
        Recipe missingRecipe = TestHelper.makeRecipe(7);
        when(repository.existsById(missingRecipe.getRecipeId())).thenReturn(false);

        Result<Recipe> result = service.save(missingRecipe);

        assertFalse(result.isSuccess());
        assertEquals("not found", result.getErrs().get(0).getMessage());
    }

    @Test
    void shouldNotSaveRecipeWithBlankTitle() {
        Recipe blankTitleRecipe = TestHelper.makeRecipe(0);
        blankTitleRecipe.setTitle("");

        Result<Recipe> result = service.save(blankTitleRecipe);

        assertFalse(result.isSuccess());
        assertEquals("recipe title is required", result.getErrs().get(0).getMessage());
    }

    @Test
    void shouldNotSaveRecipeWithNullTitle() {
        Recipe nullTitleRecipe = TestHelper.makeRecipe(0);
        nullTitleRecipe.setTitle(null);

        Result<Recipe> result = service.save(nullTitleRecipe);

        assertFalse(result.isSuccess());
        assertEquals("recipe title is required", result.getErrs().get(0).getMessage());
    }

    @Test
    void shouldNotSaveRecipeWithBlankInstructions() {
        Recipe blankInstructionsRecipe = TestHelper.makeRecipe(0);
        blankInstructionsRecipe.setInstructions("");

        Result<Recipe> result = service.save(blankInstructionsRecipe);

        assertFalse(result.isSuccess());
        assertEquals("recipe instructions are required", result.getErrs().get(0).getMessage());
    }

    @Test
    void shouldNotSaveRecipeWithNullInstructions() {
        Recipe nullInstructionsRecipe = TestHelper.makeRecipe(0);
        nullInstructionsRecipe.setInstructions(null);

        Result<Recipe> result = service.save(nullInstructionsRecipe);

        assertFalse(result.isSuccess());
        assertEquals("recipe instructions are required", result.getErrs().get(0).getMessage());
    }

    @Test
    void shouldNotSaveRecipeWithInvalidCookTime() {
        Recipe invalidCookTimeRecipe = TestHelper.makeRecipe(0);
        invalidCookTimeRecipe.setCookTime(-1);

        Result<Recipe> result = service.save(invalidCookTimeRecipe);

        assertFalse(result.isSuccess());
        assertEquals("cook time must be at least 0", result.getErrs().get(0).getMessage());
    }

    @Test
    void shouldNotSaveRecipeWithInvalidPrepTime() {
        Recipe invalidPrepTimeRecipe = TestHelper.makeRecipe(0);
        invalidPrepTimeRecipe.setPrepTime(-1);

        Result<Recipe> result = service.save(invalidPrepTimeRecipe);

        assertFalse(result.isSuccess());
        assertEquals("prep time must be at least 0", result.getErrs().get(0).getMessage());
    }

    @Test
    void shouldNotSaveRecipeWithInvalidServings() {
        Recipe invalidServingsRecipe = TestHelper.makeRecipe(0);
        invalidServingsRecipe.setServings(0);

        Result<Recipe> result = service.save(invalidServingsRecipe);

        assertFalse(result.isSuccess());
        assertEquals("servings must be greater than 0", result.getErrs().get(0).getMessage());
    }

}
