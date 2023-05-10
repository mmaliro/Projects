package learn.recipes.security;

import learn.recipes.TestHelper;
import learn.recipes.data.AppUserRepository;
import learn.recipes.models.AppUser;
import learn.recipes.validation.Result;
import learn.recipes.validation.ResultType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class AppUserServiceTest {

    @Autowired
    AppUserService service;

    @MockBean
    AppUserRepository repository;


    @Test
    void shouldAddAppUser() {
        AppUser validNewUser = TestHelper.makeAppUser(0);

        when(repository.save(validNewUser)).thenReturn(validNewUser);
        Result<AppUser> result = service.save(validNewUser);

        assertTrue(result.isSuccess());
        assertEquals(ResultType.SUCCESS, result.getType());
    }

    @Test
    void shouldUpdateAppUser() {
        AppUser validUpdateUser = TestHelper.makeAppUser(1);

        when(repository.existsById(validUpdateUser.getAppUserId())).thenReturn(true);
        when(repository.save(validUpdateUser)).thenReturn(validUpdateUser);
        Result<AppUser> result = service.save(validUpdateUser);

        assertTrue(result.isSuccess());
        assertEquals(ResultType.SUCCESS, result.getType());
    }

    @Test
    void shouldDeleteAppUser() {
        AppUser appUser = TestHelper.makeAppUser(2);
        when(repository.findById(appUser.getAppUserId())).thenReturn(Optional.of(appUser));
        assertTrue(service.deleteById(2));
    }

    @Test
    void shouldNotDeleteUserWithInvalidId() {
        AppUser invalidIdUser = TestHelper.makeAppUser(0);

        assertFalse(service.deleteById(invalidIdUser.getAppUserId()));
    }

    @Test
    void shouldNotDeleteMissingUser() {
        AppUser missingUser = TestHelper.makeAppUser(7);
        when(repository.findById(missingUser.getAppUserId())).thenReturn(Optional.empty());

        assertFalse(service.deleteById(missingUser.getAppUserId()));
    }

    // TODO when we create an AppUser, do we successfully give them the default user role?
        // I ask because UserDetails includes a getter for Authorities, and I want to check on that
    @Test
    void shouldLoadUserByUsername() {
        AppUser existingUser = TestHelper.makeAppUser(1);
        when(repository.findByUsername(existingUser.getUsername())).thenReturn(existingUser);

        UserDetails actual = service.loadUserByUsername(existingUser.getUsername());

        assertNotNull(actual);
        assertEquals("TestName", actual.getUsername());
        assertEquals("Password", actual.getPassword());
    }

    @Test
    void shouldNotLoadMissingUserByUsername() {
        AppUser missingUser = TestHelper.makeAppUser(7);
        missingUser.setUsername("Nonexistent Username");
        when(repository.findByUsername(missingUser.getUsername())).thenReturn(null);

        assertThrows(UsernameNotFoundException.class, () -> service.loadUserByUsername(missingUser.getUsername()));
    }

    @Test
    void shouldNotSaveNullUser() {
        Result<AppUser> result = service.save(null);

        assertFalse(result.isSuccess());
        assertEquals("user cannot be null", result.getErrs().get(0).getMessage());
    }

    @Test
    void shouldNotUpdateMissingUser() {
        AppUser missingUser = TestHelper.makeAppUser(7);
        when(repository.existsById(missingUser.getAppUserId())).thenReturn(false);

        Result<AppUser> result = service.save(missingUser);

        assertFalse(result.isSuccess());
        assertEquals("not found", result.getErrs().get(0).getMessage());
    }


    @Test
    void shouldNotSaveUserWithBlankUsername() {
        AppUser blankUsernameUser = TestHelper.makeAppUser(0);
        blankUsernameUser.setUsername("");

        Result<AppUser> blankResult = service.save(blankUsernameUser);

        assertFalse(blankResult.isSuccess());
        assertEquals("username is required", blankResult.getErrs().get(0).getMessage());
    }

    @Test
    void shouldNotSaveUserWithNullUsername() {
        AppUser nullUsernameUser = TestHelper.makeAppUser(0);
        nullUsernameUser.setUsername(null);

        Result<AppUser> nullResult = service.save(nullUsernameUser);

        assertFalse(nullResult.isSuccess());
        assertEquals("username is required", nullResult.getErrs().get(0).getMessage());
    }

    @Test
    void shouldNotSaveUserWithExistingUsername() {
        AppUser existingUsernameUser = TestHelper.makeAppUser(0);
        when(repository.findByUsername(existingUsernameUser.getUsername())).thenReturn(existingUsernameUser);

        Result<AppUser> result = service.save(existingUsernameUser);

        assertFalse(result.isSuccess());
        assertEquals("username already exists", result.getErrs().get(0).getMessage());
    }

    @Test
    void shouldNotSaveUserWithBlankPassword() {
        AppUser blankPasswordUser = TestHelper.makeAppUser(0);
        blankPasswordUser.setPassword("");

        Result<AppUser> blankResult = service.save(blankPasswordUser);

        assertFalse(blankResult.isSuccess());
        assertEquals("password is required", blankResult.getErrs().get(0).getMessage());
    }

    @Test
    void shouldNotSaveUserWithNullPassword() {
        AppUser nullPasswordUser = TestHelper.makeAppUser(0);
        nullPasswordUser.setPassword(null);

        Result<AppUser> nullResult = service.save(nullPasswordUser);

        assertFalse(nullResult.isSuccess());
        assertEquals("password is required", nullResult.getErrs().get(0).getMessage());
    }

    @Test
    void shouldNotSaveUserWithBlankFirstName() {
        AppUser blankFirstNameUser = TestHelper.makeAppUser(0);
        blankFirstNameUser.setFirstName("");

        Result<AppUser> blankResult = service.save(blankFirstNameUser);

        assertFalse(blankResult.isSuccess());
        assertEquals("first name is required", blankResult.getErrs().get(0).getMessage());

    }

    @Test
    void shouldNotSaveUserWithNullFirstName() {

        AppUser nullFirstNameUser = TestHelper.makeAppUser(0);
        nullFirstNameUser.setFirstName(null);

        Result<AppUser> nullResult = service.save(nullFirstNameUser);

        assertFalse(nullResult.isSuccess());
        assertEquals("first name is required", nullResult.getErrs().get(0).getMessage());
    }

    @Test
    void shouldNotSaveUserWithBlankLastName() {
        AppUser blankLastNameUser = TestHelper.makeAppUser(0);
        blankLastNameUser.setLastName("");

        Result<AppUser> blankResult = service.save(blankLastNameUser);

        assertFalse(blankResult.isSuccess());
        assertEquals("last name is required", blankResult.getErrs().get(0).getMessage());
    }

    @Test
    void shouldNotSaveUserWithNullLastName() {
        AppUser nullLastNameUser = TestHelper.makeAppUser(0);
        nullLastNameUser.setLastName(null);

        Result<AppUser> nullResult = service.save(nullLastNameUser);

        assertFalse(nullResult.isSuccess());
        assertEquals("last name is required", nullResult.getErrs().get(0).getMessage());
    }

    @Test
    void shouldNotSaveUserWithBlankEmail() {
        AppUser blankEmailUser = TestHelper.makeAppUser(0);
        blankEmailUser.setEmail("");

        Result<AppUser> blankResult = service.save(blankEmailUser);

        assertFalse(blankResult.isSuccess());
        assertEquals("email is required", blankResult.getErrs().get(0).getMessage());
    }

    @Test
    void shouldNotSaveUserWithNullEmail() {

        AppUser nullEmailUser = TestHelper.makeAppUser(0);
        nullEmailUser.setEmail(null);

        Result<AppUser> nullResult = service.save(nullEmailUser);

        assertFalse(nullResult.isSuccess());
        assertEquals("email is required", nullResult.getErrs().get(0).getMessage());

    }

    @Test
    void shouldNotSaveUserWithExistingEmail() {
        AppUser existingEmailUser = TestHelper.makeAppUser(0);
        when(repository.findByUsername(existingEmailUser.getUsername())).thenReturn(null);
        when(repository.findByEmail(existingEmailUser.getEmail())).thenReturn(existingEmailUser);

        Result<AppUser> result = service.save(existingEmailUser);

        assertFalse(result.isSuccess());
        assertEquals("an account with this email already exists", result.getErrs().get(0).getMessage());
    }

    @Test
    void shouldNotSaveUserWithFutureBirthday() {
        AppUser existingUsernameUser = TestHelper.makeAppUser(7);
        existingUsernameUser.setDob(LocalDate.now().plusDays(1));
        when(repository.existsById(7)).thenReturn(true);
        when(repository.findByUsername(existingUsernameUser.getUsername())).thenReturn(null);

        Result<AppUser> result = service.save(existingUsernameUser);

        assertFalse(result.isSuccess());
        assertEquals("date of birth cannot be in the future", result.getErrs().get(0).getMessage());
    }

}
