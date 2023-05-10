package learn.recipes.controllers;

import jakarta.validation.Valid;
import learn.recipes.domain.FoodService;
import learn.recipes.models.Food;
import learn.recipes.models.Meal;
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
@RequestMapping("/api/food")
public class FoodController {
    private final FoodService foodService;

    public FoodController(FoodService foodService) {
        this.foodService = foodService;
    }


    @GetMapping
    public List<Food> findAll() {return foodService.findAll(); }


    @GetMapping("/{foodId}")
    public ResponseEntity<Food> findById(@PathVariable int foodId) {
        Food food = foodService.findById(foodId);
        if(food == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(food, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> add(@RequestBody @Valid Food food, BindingResult bindingResult) {
        Result<Food> result = foodService.save(food);
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(mapErrs(bindingResult), HttpStatus.BAD_REQUEST);
        }

        if (food.getFoodId() == 0) {
            return new ResponseEntity<>(mapErrs("foodId", "food Id has not been set"), HttpStatus.BAD_REQUEST);
        }

        if (!result.isSuccess()) {
            return new ResponseEntity<>(result.getErrs(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(result.getPayload(), HttpStatus.CREATED);
    }

    @PutMapping("/{foodId}")
    public ResponseEntity<Object> update(@PathVariable int foodId,
                                         @RequestBody @Valid Food food,
                                         BindingResult bindingResult) {
        if (foodId != food.getFoodId()) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(mapErrs(bindingResult), HttpStatus.BAD_REQUEST);
        }

        if (food.getFoodId() <= 0) {
            return new ResponseEntity<>(mapErrs("foodId", "food Id must be set"), HttpStatus.BAD_REQUEST);
        }

        Result<Food> result = foodService.save(food);
        if (!result.isSuccess()) {
            if (result.getType() == ResultType.NOT_FOUND) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(result.getErrs(), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(result.getPayload(), HttpStatus.OK);
    }

    @DeleteMapping("/{foodId}")
    public ResponseEntity<Void> deleteById(@PathVariable int foodId) {
        if (foodService.deleteById(foodId)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
