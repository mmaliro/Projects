package learn.recipes.data;

import jakarta.transaction.Transactional;
import learn.recipes.TestHelper;
import learn.recipes.models.Food;
import learn.recipes.models.Ingredients;
import learn.recipes.models.Tags;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class IngredientsRepositoryTest {

    @Autowired
    IngredientsRepository repository;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setup() {jdbcTemplate.update("call set_known_good_state();");}

    @Test
    @Transactional
    void shouldAddTag() {
        Ingredients newIngredient = new Ingredients();
        Food ingredientFood = TestHelper.makeFood(3);
        newIngredient.setIngredientId(0);
        newIngredient.setFood(ingredientFood);
        newIngredient.setRecipeId(3);
        newIngredient.setAmount(2.5);
        newIngredient.setMeasurementUnit("oz");
        Ingredients actual = repository.save(newIngredient);
        assertEquals("oz", actual.getMeasurementUnit());
        assertEquals(2.5, actual.getAmount());
    }


}
