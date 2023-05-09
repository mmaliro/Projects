package learn.bec.data;

import learn.bec.models.Donation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class DonationRepositoryTest {

    @Autowired
    DonationRepository repository;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setup() {
        jdbcTemplate.update("call set_known_good_state();");
    }

    @Test
    @DisplayName("Should throw an exception when the donation entity is null")
    void saveDonationEntityWhenNullThenThrowException() {
        assertThrows(IllegalArgumentException.class, () -> repository.save(null));
    }

    @Test
    @DisplayName("Should save the donation entity successfully")
    void saveDonationEntitySuccessfully() {
        BigDecimal amount = new BigDecimal("100.00");
        LocalDate donationDate = LocalDate.now();
        String note = "Test donation";
        Donation donation = new Donation(amount, donationDate, note);

        Donation savedDonation = repository.save(donation);

        assertNotNull(savedDonation);
        assertNotEquals(0, savedDonation.getDonationId());
        assertEquals(amount, savedDonation.getAmount());
        assertEquals(donationDate, savedDonation.getDonationDate());
        assertEquals(note, savedDonation.getNote());
    }
}