package learn.bec.controllers;

import jakarta.validation.Valid;
import learn.bec.domain.EventPlanService;
import learn.bec.models.EventPlan;
import learn.bec.validation.Result;
import learn.bec.validation.ResultType;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static learn.bec.controllers.ErrMapper.mapErrs;

@RestController
@CrossOrigin
@RequestMapping("/api/event")
public class EventPlanController {

    private final EventPlanService eventPlanService;

    public EventPlanController(EventPlanService eventPlanService) {
        this.eventPlanService = eventPlanService;
    }

    @GetMapping
    public List<EventPlan> findAll() {return eventPlanService.findAll(); }

    @GetMapping("/{eventPlanId}")
    public ResponseEntity<EventPlan> findById(@PathVariable int eventPlanId) {
        EventPlan eventPlan = eventPlanService.findById(eventPlanId);
        if(eventPlan == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(eventPlan, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> add(@RequestBody @Valid EventPlan eventPlan, BindingResult bindingResult) {
        Result<EventPlan> result = eventPlanService.save(eventPlan);
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(mapErrs(bindingResult), HttpStatus.BAD_REQUEST);
        }

        if (eventPlan.getEventPlanId() == 0) {
            return new ResponseEntity<>(mapErrs("eventPlanId", "event plan Id has not been set"), HttpStatus.BAD_REQUEST);
        }

        if (!result.isSuccess()) {
            return new ResponseEntity<>(result.getErrs(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(result.getPayload(), HttpStatus.CREATED);
    }

    @PutMapping("/{eventPlanId}")
    public ResponseEntity<Object> update(@PathVariable int eventPlanId,
                                         @RequestBody @Valid EventPlan eventPlan,
                                         BindingResult bindingResult) {
        if (eventPlanId != eventPlan.getEventPlanId()) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(mapErrs(bindingResult), HttpStatus.BAD_REQUEST);
        }

        if (eventPlan.getEventPlanId() <= 0) {
            return new ResponseEntity<>(mapErrs("eventPlanId", "event plan Id must be set"), HttpStatus.BAD_REQUEST);
        }

        Result<EventPlan> result = eventPlanService.save(eventPlan);
        if (!result.isSuccess()) {
            if (result.getType() == ResultType.NOT_FOUND) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(result.getErrs(), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(result.getPayload(), HttpStatus.OK);
    }

    @DeleteMapping("/{eventPlanId}")
    public ResponseEntity<Void> deleteById(@PathVariable int eventPlanId) {
        if (eventPlanService.deleteById(eventPlanId)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
