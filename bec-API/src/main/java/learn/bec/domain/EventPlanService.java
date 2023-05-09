package learn.bec.domain;

import learn.bec.data.EventPlanRepository;
import learn.bec.models.EventPlan;
import learn.bec.validation.Result;
import learn.bec.validation.ResultType;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventPlanService {

    private final EventPlanRepository eventPlanRepository;


    public EventPlanService(EventPlanRepository eventPlanRepository) {
        this.eventPlanRepository = eventPlanRepository;
    }

    public List<EventPlan> findAll() {return eventPlanRepository.findAll(); }

    public EventPlan findById(int eventPlanId) {return eventPlanRepository.findById(eventPlanId).orElse(null);}

    public Result<EventPlan> save(EventPlan eventPlan) {
        Result<EventPlan> result = validate(eventPlan);
        if(!result.isSuccess()) {
            return result;
        }

        EventPlan payload = eventPlanRepository.save(eventPlan);
        result.setPayload(payload);
        return result;

    }

    public boolean deleteById(int foodId) {
        if (foodId <= 0) {
            return false;
        }
        if (eventPlanRepository.findById(foodId).isEmpty()) {
            return false;
        }

        eventPlanRepository.deleteById(foodId);
        return true;
    }

    private Result<EventPlan> validate(EventPlan eventPlan) {
        Result<EventPlan> result = new Result<>();

        if (eventPlan == null) {
            result.addErr("", "food cannot be null", ResultType.INVALID);
            return result;
        }

        if (eventPlan.getEventPlanId() > 0) {
            if (!eventPlanRepository.existsById(eventPlan.getEventPlanId())) {
                result.addErr("", "not found", ResultType.NOT_FOUND);
                return result;
            }
        }

        if(Validations.isNullOrBlank(eventPlan.getEventName())) {
            result.addErr("", "Event name is required", ResultType.INVALID);
        }
        if(eventPlanRepository.findByEventName(eventPlan.getEventName()) != null) {
            result.addErr("", "Event name must be unique", ResultType.ALREADY_EXISTS);
        }
        return result;
    }

}
