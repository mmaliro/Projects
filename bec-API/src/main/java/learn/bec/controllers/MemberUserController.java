package learn.bec.controllers;

import jakarta.validation.Valid;
import learn.bec.domain.MemberUserService;
import learn.bec.models.MemberUser;
import learn.bec.validation.Result;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import learn.bec.validation.ResultType;

import java.util.List;

import static learn.bec.controllers.ErrMapper.mapErrs;

@RestController
@CrossOrigin
@RequestMapping("/api/memberUser")
public class MemberUserController {

    private final MemberUserService memberUserService;


    public MemberUserController(MemberUserService memberUserService) {
        this.memberUserService = memberUserService;
    }

    @GetMapping
    public List<MemberUser> findAll() {return memberUserService.findAll();}

    @GetMapping("/{memberUserId}")
    public ResponseEntity<MemberUser> findById(@PathVariable int memberUserId) {
        MemberUser user = memberUserService.findById(memberUserId);
        if(user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> add(@RequestBody @Valid MemberUser user, BindingResult bindingResult) {
        Result<MemberUser> result = memberUserService.save(user);
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(mapErrs(bindingResult), HttpStatus.BAD_REQUEST);
        }

        if (user.getMemberUserId() == 0) {
            return new ResponseEntity<>(mapErrs("memberUserId", "memberUserId has not been set"), HttpStatus.BAD_REQUEST);
        }

        if (!result.isSuccess()) {
            return new ResponseEntity<>(result.getErrs(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(result.getPayload(), HttpStatus.CREATED);

    }

    @PutMapping("/{memberUserId}")
    public ResponseEntity<Object> update(@PathVariable int memberUserId,
                                         @RequestBody @Valid MemberUser user,
                                         BindingResult bindingResult) {
        if (memberUserId != user.getMemberUserId()) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(mapErrs(bindingResult), HttpStatus.BAD_REQUEST);
        }

        if (user.getMemberUserId() <= 0) {
            return new ResponseEntity<>(mapErrs("memberUserId", "userId must be set"), HttpStatus.BAD_REQUEST);
        }

        Result<MemberUser> result = memberUserService.save(user);
        if (!result.isSuccess()) {
            if (result.getType() == ResultType.NOT_FOUND) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(result.getErrs(), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(result.getPayload(), HttpStatus.OK);
    }

    @DeleteMapping("/{memberUserId}")
    public ResponseEntity<Void> deleteById(@PathVariable int appUserId) {
        if (memberUserService.deleteById(appUserId)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
