package learn.recipes.controllers;

import jakarta.validation.Valid;
import learn.recipes.domain.RecipeService;
import learn.recipes.models.AppUser;
import learn.recipes.models.Recipe;
import learn.recipes.validation.Result;
import learn.recipes.validation.ResultType;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static learn.recipes.controllers.ErrMapper.mapErrs;

@RestController
@CrossOrigin
@RequestMapping("/api/recipe")
public class RecipeController {
    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }


    @GetMapping
    public List<Recipe> findAll() {return recipeService.findAll(); }

    @GetMapping("/{recipeId}")
    public ResponseEntity<Recipe> findById(@PathVariable int recipeId) {
        Recipe recipe = recipeService.findById(recipeId);
        if(recipe == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(recipe, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> add(@RequestBody @Valid Recipe recipe, BindingResult bindingResult) {
        Result<Recipe> result = recipeService.save(recipe);
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(mapErrs(bindingResult), HttpStatus.BAD_REQUEST);
        }

        if (recipe.getRecipeId() == 0) {
            return new ResponseEntity<>(mapErrs("recipeId", "recipe Id has not been set"), HttpStatus.BAD_REQUEST);
        }

        if (!result.isSuccess()) {
            return new ResponseEntity<>(result.getErrs(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(result.getPayload(), HttpStatus.CREATED);
    }

    @PutMapping("/{recipeId}")
    public ResponseEntity<Object> update(@PathVariable int recipeId,
                                         @RequestBody @Valid Recipe recipe,
                                         BindingResult bindingResult) {
        if (recipeId != recipe.getRecipeId()) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(mapErrs(bindingResult), HttpStatus.BAD_REQUEST);
        }

        if (recipe.getRecipeId() <= 0) {
            return new ResponseEntity<>(mapErrs("recipeId", "recipe Id must be set"), HttpStatus.BAD_REQUEST);
        }

        Result<Recipe> result = recipeService.save(recipe);
        if (!result.isSuccess()) {
            if (result.getType() == ResultType.NOT_FOUND) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(result.getErrs(), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(result.getPayload(), HttpStatus.OK);
    }

    @DeleteMapping("/{recipeId}")
    public ResponseEntity<Void> deleteById(@PathVariable int recipeId) {
        if (recipeService.deleteById(recipeId)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
