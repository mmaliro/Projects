package learn.bec.data;

import learn.bec.models.EventPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventPlanRepository extends JpaRepository<EventPlan, Integer> {

    EventPlan findByEventName(String eventName);

}
