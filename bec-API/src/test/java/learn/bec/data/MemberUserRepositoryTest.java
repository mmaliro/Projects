package learn.bec.data;

import jakarta.transaction.Transactional;
import learn.bec.models.MemberUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class MemberUserRepositoryTest {

    @Autowired
    MemberUserRepository repository;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setup() {
        jdbcTemplate.update("call set_known_good_state();");
    }

    @Test
    @Transactional
    @DisplayName("Should return null when the username does not exist")
    void findByUsernameWhenUsernameDoesNotExist() {
        String nonExistentUsername = "nonExistentUser";
        MemberUser result = repository.findByUsername(nonExistentUsername);
        assertNull(result, "Expected null when the username does not exist");
    }

    @Test
    @Transactional
    @DisplayName("Should return the member user when the username exists")
    void findByUsernameWhenUsernameExists() {
        String existingUsername = "testUsername";

        MemberUser foundMemberUser = repository.findByUsername(existingUsername);

        assertNotNull(foundMemberUser, "MemberUser should not be null");
        assertEquals(existingUsername, foundMemberUser.getUsername(), "Usernames should match");
    }
}