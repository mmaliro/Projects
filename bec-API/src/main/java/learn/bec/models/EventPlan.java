package learn.bec.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
public class EventPlan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int eventPlanId;

    @NotNull
    @NotBlank
    private String eventName;

    private String eventDescription;

    @NotNull
    private LocalDate eventDate;

    @NotNull
    private LocalTime eventTime;

    @NotNull
    private String location;

    @ToString.Exclude
    @ManyToMany
    @JoinTable(
            name = "event_plan_app_role",
            joinColumns = @JoinColumn(name = "event_plan_id"),
            inverseJoinColumns = @JoinColumn(name = "app_role_id"))
    private Set<AppRole> appRoles = new HashSet<>();


}
