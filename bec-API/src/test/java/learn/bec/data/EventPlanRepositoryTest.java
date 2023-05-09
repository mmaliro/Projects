package learn.bec.data;

import learn.bec.models.EventPlan;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class EventPlanRepositoryTest {

    @Autowired
    EventPlanRepository repository;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setup() {
        jdbcTemplate.update("call set_known_good_state();");
    }

    @Test
    @DisplayName("Should return null when the event name is not found")
    void findByEventNameWhenEventNameIsNotFound() {
        String eventName = "NonExistentEvent";
        EventPlan result = repository.findByEventName(eventName);
        assertNull(result, "Expected null when event name is not found");
    }

    @Test
    @DisplayName("Should return the event plan when the event name is found")
    void findByEventNameWhenEventNameIsFound() {
        String eventName = "Test Event";
        EventPlan expectedEventPlan = new EventPlan();
        expectedEventPlan.setEventName(eventName);
        expectedEventPlan.setEventDescription("Test Event Description");
        expectedEventPlan.setEventDate(LocalDate.now());
        expectedEventPlan.setEventTime(LocalTime.now());
        expectedEventPlan.setLocation("Test Location");
        repository.save(expectedEventPlan);

        EventPlan actualEventPlan = repository.findByEventName(eventName);

        assertNotNull(actualEventPlan);
        assertEquals(expectedEventPlan.getEventPlanId(), actualEventPlan.getEventPlanId());
        assertEquals(expectedEventPlan.getEventName(), actualEventPlan.getEventName());
        assertEquals(
                expectedEventPlan.getEventDescription(), actualEventPlan.getEventDescription());
        assertEquals(expectedEventPlan.getEventDate(), actualEventPlan.getEventDate());
        assertEquals(expectedEventPlan.getEventTime(), actualEventPlan.getEventTime());
        assertEquals(expectedEventPlan.getLocation(), actualEventPlan.getLocation());
    }
}