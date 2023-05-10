package learn.recipes.controllers;

import jakarta.validation.Valid;
import learn.recipes.domain.TagsService;
import learn.recipes.models.Recipe;
import learn.recipes.models.Tags;
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
@RequestMapping("/api/tags")
public class TagsController {
    private final TagsService tagsService;

    public TagsController(TagsService tagsService) {
        this.tagsService = tagsService;
    }

    @GetMapping
    public List<Tags> findAll() {return tagsService.findAll(); }

    @GetMapping("/{tagId}")
    public ResponseEntity<Tags> findById(@PathVariable int tagId) {
        Tags tag = tagsService.findById(tagId);
        if(tag == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(tag, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> add(@RequestBody @Valid Tags tag, BindingResult bindingResult) {
        Result<Tags> result = tagsService.save(tag);
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(mapErrs(bindingResult), HttpStatus.BAD_REQUEST);
        }

        if (tag.getTagId() == 0) {
            return new ResponseEntity<>(mapErrs("tagId", "tag Id has not been set"), HttpStatus.BAD_REQUEST);
        }

        if (!result.isSuccess()) {
            return new ResponseEntity<>(result.getErrs(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(result.getPayload(), HttpStatus.CREATED);
    }

    @PutMapping("/{tagId}")
    public ResponseEntity<Object> update(@PathVariable int tagId,
                                         @RequestBody @Valid Tags tag,
                                         BindingResult bindingResult) {
        if (tagId != tag.getTagId()) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(mapErrs(bindingResult), HttpStatus.BAD_REQUEST);
        }

        if (tag.getTagId() <= 0) {
            return new ResponseEntity<>(mapErrs("tagId", "tag Id must be set"), HttpStatus.BAD_REQUEST);
        }

        Result<Tags> result = tagsService.save(tag);
        if (!result.isSuccess()) {
            if (result.getType() == ResultType.NOT_FOUND) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(result.getErrs(), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(result.getPayload(), HttpStatus.OK);
    }

    @DeleteMapping("/{tagId}")
    public ResponseEntity<Void> deleteById(@PathVariable int tagId) {
        if (tagsService.deleteById(tagId)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
