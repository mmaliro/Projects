package learn.recipes.controllers;

import jakarta.validation.Valid;
import learn.recipes.domain.MealService;
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
@RequestMapping("/api/meal")
public class MealController {

    private final MealService mealService;

    public MealController(MealService mealService) {
        this.mealService = mealService;
    }


    @GetMapping
    public List<Meal> findAll() {return mealService.findAll(); }


    @GetMapping("/{mealId}")
    public ResponseEntity<Meal> findById(@PathVariable int mealId) {
        Meal meal = mealService.findById(mealId);
        if(meal == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(meal, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> add(@RequestBody @Valid Meal meal, BindingResult bindingResult) {
        Result<Meal> result = mealService.save(meal);
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(mapErrs(bindingResult), HttpStatus.BAD_REQUEST);
        }

        if (meal.getMealId() == 0) {
            return new ResponseEntity<>(mapErrs("mealId", "meal Id has not been set"), HttpStatus.BAD_REQUEST);
        }

        if (!result.isSuccess()) {
            return new ResponseEntity<>(result.getErrs(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(result.getPayload(), HttpStatus.CREATED);
    }

    @PutMapping("/{mealId}")
    public ResponseEntity<Object> update(@PathVariable int mealId,
                                         @RequestBody @Valid Meal meal,
                                         BindingResult bindingResult) {
        if (mealId != meal.getMealId()) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(mapErrs(bindingResult), HttpStatus.BAD_REQUEST);
        }

        if (meal.getMealId() == 0) {
            return new ResponseEntity<>(mapErrs("mealId", "meal Id must be set"), HttpStatus.BAD_REQUEST);
        }

        Result<Meal> result = mealService.save(meal);
        if (!result.isSuccess()) {
            if (result.getType() == ResultType.NOT_FOUND) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(result.getErrs(), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(result.getPayload(), HttpStatus.OK);
    }

    @DeleteMapping("/{mealId}")
    public ResponseEntity<Void> deleteById(@PathVariable int mealId) {
        if (mealService.deleteById(mealId)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}