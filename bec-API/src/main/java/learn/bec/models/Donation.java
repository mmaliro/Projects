package learn.bec.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;


@Entity
@Data
@NoArgsConstructor
public class Donation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "donation_id")
    private int donationId;
    @NotNull
    private BigDecimal amount;

    private String note;
    @NotNull
    private LocalDate donationDate;


    @ManyToOne
    @JoinColumn(name = "member_user_id")
    private MemberUser memberUser;


    public Donation(BigDecimal amount, LocalDate donationDate, String note) {
        this.amount = amount;
        this.donationDate = donationDate;
        this.note = note;
    }
}

