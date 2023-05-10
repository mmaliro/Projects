package learn.recipes.controllers;

import jakarta.validation.Valid;
import learn.recipes.security.AppUserService;
import learn.recipes.models.AppUser;
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
@RequestMapping("/api/appUser")
public class AppUserController {

    private final AppUserService appUserService;

    public AppUserController(AppUserService appUserService) {
        this.appUserService = appUserService;
    }
    @GetMapping
    public List<AppUser> findAll() {return appUserService.findAll(); }
    @GetMapping("/{appUserId}")
    public ResponseEntity<AppUser> findById(@PathVariable int appUserId) {
        AppUser user = appUserService.findById(appUserId);
        if(user == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> add(@RequestBody @Valid AppUser user, BindingResult bindingResult) {
        Result<AppUser> result = appUserService.save(user);
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(mapErrs(bindingResult), HttpStatus.BAD_REQUEST);
        }

        if (user.getAppUserId() == 0) {
            return new ResponseEntity<>(mapErrs("appUserId", "appUserId has not been set"), HttpStatus.BAD_REQUEST);
        }

        if (!result.isSuccess()) {
            return new ResponseEntity<>(result.getErrs(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(result.getPayload(), HttpStatus.CREATED);
    }

    @PutMapping("/{appUserId}")
    public ResponseEntity<Object> update(@PathVariable int appUserId,
                                         @RequestBody @Valid AppUser user,
                                         BindingResult bindingResult) {
        if (appUserId != user.getAppUserId()) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(mapErrs(bindingResult), HttpStatus.BAD_REQUEST);
        }

        if (user.getAppUserId() <= 0) {
            return new ResponseEntity<>(mapErrs("appUserId", "userId must be set"), HttpStatus.BAD_REQUEST);
        }

        Result<AppUser> result = appUserService.save(user);
        if (!result.isSuccess()) {
            if (result.getType() == ResultType.NOT_FOUND) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(result.getErrs(), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(result.getPayload(), HttpStatus.OK);
    }

    @DeleteMapping("/{appUserId}")
    public ResponseEntity<Void> deleteById(@PathVariable int appUserId) {
        if (appUserService.deleteById(appUserId)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
