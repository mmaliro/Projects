package learn.recipes.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Ingredients {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ingredientId;
    private int recipeId;
    private double amount;
    private String measurementUnit;

    @ManyToOne
    @JoinColumn (name = "foodId")
    private Food food;
}

