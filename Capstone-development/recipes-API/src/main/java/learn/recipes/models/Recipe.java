package learn.recipes.models;


import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.URL;

import java.util.*;

@Entity
@Data
@NoArgsConstructor
public class Recipe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int recipeId;
    @NotNull
    @NotBlank
    private String title;
    @NotNull
    @NotBlank
    private String instructions;

    private String recipeDescription;
    @Min(value = 0)
    private int cookTime;
    @Min(value = 0)
    private int prepTime;

    private Integer calories;
    @Min(value = 1)
    private int servings;
    @URL
    private String imageUrl;

    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(name = "recipe_tags",
            joinColumns = @JoinColumn(name = "recipe_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private Set<Tags> tags = new HashSet<>();

    @OneToMany(mappedBy = "recipeId", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Ingredients> ingredients = new ArrayList<>();

//    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
//    @JoinTable(name = "ingredients",
//            joinColumns = @JoinColumn(name = "recipe_id"),
//            inverseJoinColumns = @JoinColumn(name = "food_id")
//    )
//    private List<Food> foods = new ArrayList<>();

//    public void addFoods(Collection<String> foodNames) {
//        ingredients.clear();
//        for (String name : foodNames) {
//            Food food = new Food();
//            food.setFoodName(name);
//            ingredients.add(food);
//        }
//    }
}
