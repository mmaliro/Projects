package learn.recipes.data;


import learn.recipes.models.AppUser;
import learn.recipes.models.Food;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FoodRepository extends JpaRepository<Food, Integer> {

    Food findByFoodName(String foodName);

}
