package learn.recipes.data;

import jakarta.transaction.Transactional;
import learn.recipes.TestHelper;
import learn.recipes.models.AppUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class AppUserRepositoryTest {

    @Autowired
    AppUserRepository repository;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setup() {
        jdbcTemplate.update("call set_known_good_state();");
    }


    @Test
    @Transactional
    void shouldFindAll2To4Users() {
        List<AppUser> allUsers = repository.findAll();
        assertNotNull(allUsers);
        assertTrue(allUsers.size() >= 2 && allUsers.size() <= 4);
    }

    @Test
    @Transactional
    void shouldFindUserById() {
        AppUser existingUser = repository.findById(3).orElse(null);
        assertNotNull(existingUser);
        assertEquals("adminuser@adminuser.com", existingUser.getEmail());
        assertEquals(LocalDate.of(2000, 01, 01), existingUser.getDob());
    }

    @Test
    @Transactional
    void shouldNotFindMissingUserById() {
        assertNull(repository.findById(7).orElse(null));
    }

    @Test
    @Transactional
    void shouldFindUserByUsername() {
        AppUser existingUser = repository.findByUsername("adminuser");
        assertNotNull(existingUser);
        assertEquals(3, existingUser.getAppUserId());
        assertEquals("adminuserlast", existingUser.getLastName());
    }

    @Test
    @Transactional
    void shouldNotFindMissingUserByUsername() {
        assertNull(repository.findByUsername("missing user"));
    }

    @Test
    @Transactional
    void shouldFindUserByEmail() {
        AppUser existingUser = repository.findByEmail("adminuser@adminuser.com");
        assertNotNull(existingUser);
        assertEquals(3, existingUser.getAppUserId());
        assertEquals("adminuserlast", existingUser.getLastName());
    }

    @Test
    @Transactional
    void shouldNotFindMissingUserByEmail() {
        assertNull(repository.findByUsername("email@email.com"));
    }

    @Test
    @Transactional
    void shouldAddAppUser() {
        AppUser createdNewUser = TestHelper.makeAppUser(0);
        AppUser newUser = repository.save(createdNewUser);
        assertEquals(4, newUser.getAppUserId());

        AppUser expectedNewUser = TestHelper.makeAppUser(4);
        AppUser actual = repository.findById(4).orElse(null);

        assertEquals(expectedNewUser, actual);
        assertEquals("Password", expectedNewUser.getPassword());
        assertEquals("TestEmail@email.com", expectedNewUser.getEmail());
        assertTrue(expectedNewUser.isEnabled());
    }

    // TODO: why won't this one pass? I get an AssertionError because the deleteById function isn't working for some reason
    @Test
    @Transactional
    void shouldDeleteAppUser() {
        repository.deleteById(2);
        assertNull(repository.findById(2).orElse(null));
        assertNull(repository.findByUsername("admin"));
    }

    @Test
    @Transactional
    void shouldUpdateExistingAppUser() {
        AppUser user = repository.findById(1).orElse(null);
        user.setFirstName("First Name Update");
        user.setUsername("Username Update");
        repository.save(user);

        AppUser updatedUser = repository.findById(1).orElse(null);
        assertEquals("First Name Update", updatedUser.getFirstName());
        assertEquals("Username Update", updatedUser.getUsername());
    }
}