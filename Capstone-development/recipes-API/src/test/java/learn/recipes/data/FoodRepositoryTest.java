package learn.recipes.data;

import jakarta.transaction.Transactional;
import learn.recipes.TestHelper;
import learn.recipes.models.Food;
import learn.recipes.models.Tags;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class FoodRepositoryTest {

    @Autowired
    FoodRepository repository;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setup() {jdbcTemplate.update("call set_known_good_state();");}

    @Test
    @Transactional
    void shouldFind12To14Foods() {
        List<Food> allFood = repository.findAll();
        assertNotNull(allFood);
        assertTrue(allFood.size() >= 12 && allFood.size() <= 14);
    }

    @Test
    @Transactional
    void shouldFindFoodById() {
        Food existingFood = repository.findById(1).orElse(null);
        assertNotNull(existingFood);
        assertEquals("chicken", existingFood.getFoodName());
    }

    @Test
    @Transactional
    void shouldNotFindMissingFoodById() {
        assertNull(repository.findById(15).orElse(null));
    }

    @Test
    @Transactional
    void shouldFindFoodByName() {
        Food existingFood = repository.findByFoodName("chicken");
        assertNotNull(existingFood);
        assertEquals(1, existingFood.getFoodId());

    }

    @Test
    @Transactional
    void shouldNotFindMissingFoodByName() {
        assertNull(repository.findByFoodName("nonexistent food name"));
    }

    @Test
    @Transactional
    void shouldAddFood() {
        Food newFood = TestHelper.makeFood(0);
        Food actual = repository.save(newFood);
        assertEquals(14, actual.getFoodId());

        Food expectedNewFood = TestHelper.makeFood(14);
        actual = repository.findById(expectedNewFood.getFoodId()).orElse(null);
        assertNotNull(actual);
        assertEquals(expectedNewFood, actual);
    }

    @Test
    @Transactional
    void shouldDeleteFood() {
        repository.deleteById(2);
        assertNull(repository.findById(2).orElse(null));
    }

    @Test
    @Transactional
    void shouldUpdateExistingFood() {
        Food food = repository.findById(1).orElse(null);
        food.setFoodName("Test Name Update");
        repository.save(food);

        Food updatedFood = repository.findById(1).orElse(null);
        assertEquals("Test Name Update", updatedFood.getFoodName());
    }

}