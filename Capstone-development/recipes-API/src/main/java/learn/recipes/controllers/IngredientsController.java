package learn.recipes.controllers;

import jakarta.validation.Valid;
import learn.recipes.domain.IngredientsService;
import learn.recipes.models.Ingredients;
import learn.recipes.validation.Result;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import static learn.recipes.controllers.ErrMapper.mapErrs;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class IngredientsController {

    private final IngredientsService ingredientsService;

    public IngredientsController(IngredientsService ingredientsService) {
        this.ingredientsService = ingredientsService;
    }

    @PostMapping("/ingredients")
    public ResponseEntity<?> add(@RequestBody @Valid Ingredients ingredient, BindingResult bindingResult) {
        Result<Ingredients> result = ingredientsService.save(ingredient);
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(mapErrs(bindingResult), HttpStatus.BAD_REQUEST);
        }

        if (ingredient.getIngredientId() <= 0) {
            return new ResponseEntity<>(mapErrs("ingredientId", "ingredient ID has not been set"), HttpStatus.BAD_REQUEST);
        }

        if (!result.isSuccess()) {
            return new ResponseEntity<>(result.getErrs(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(result.getPayload(), HttpStatus.CREATED);
    }

}
