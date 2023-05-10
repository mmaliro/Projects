package learn.recipes.security;

import learn.recipes.data.AppUserRepository;
import learn.recipes.domain.Validations;
import learn.recipes.models.AppUser;
import learn.recipes.validation.Result;
import learn.recipes.validation.ResultType;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class AppUserService implements UserDetailsService {
    private final AppUserRepository appUserRepository;


    public AppUserService(AppUserRepository appUserRepository) {
        this.appUserRepository = appUserRepository;
    }

    public List<AppUser> findAll() {return appUserRepository.findAll(); }

    public AppUser findById(int appUserId) {return appUserRepository.findById(appUserId).orElse(null);}

    public Result<AppUser> save(AppUser user) {
        Result<AppUser> result = validate(user);
        if(!result.isSuccess()) {
            return result;
        }

        AppUser payload = appUserRepository.save(user);
        result.setPayload(payload);
        return result;

    }


    public boolean deleteById(int appUserId) {
        if (appUserId <= 0) {
            return false;
        }
        if (appUserRepository.findById(appUserId).isEmpty()) {
            return false;
        }

        appUserRepository.deleteById(appUserId);
        return true;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser user = appUserRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(String.format("%s not found", username));
        }
        return user;
    }


    private Result<AppUser> validate(AppUser user) {
        Result<AppUser> result = new Result<>();

        if (user == null) {
            result.addErr("", "user cannot be null", ResultType.INVALID);
            return result;
        }

        if (user.getAppUserId() > 0) {
            if (!appUserRepository.existsById(user.getAppUserId())) {
                result.addErr("", "not found", ResultType.NOT_FOUND);
                return result;
            }
        }

        if(Validations.isNullOrBlank(user.getUsername())) {
            result.addErr("", "username is required", ResultType.INVALID);
        }
        if(appUserRepository.findByUsername(user.getUsername()) != null) {
            result.addErr("", "username already exists", ResultType.ALREADY_EXISTS);
        }

        if(Validations.isNullOrBlank(user.getPassword())) {
            result.addErr("", "password is required", ResultType.INVALID);
        }

        if(Validations.isNullOrBlank(user.getFirstName())) {
            result.addErr("", "first name is required", ResultType.INVALID);
        }

        if(Validations.isNullOrBlank(user.getLastName())) {
            result.addErr("", "last name is required", ResultType.INVALID);
        }

        if(Validations.isNullOrBlank(user.getEmail())) {
            result.addErr("", "email is required", ResultType.INVALID);
        }
        if(appUserRepository.findByEmail(user.getEmail()) != null) {
            result.addErr("", "an account with this email already exists", ResultType.ALREADY_EXISTS);
        }

        if(user.getDob().isAfter(LocalDate.now())) {
            result.addErr("", "date of birth cannot be in the future", ResultType.INVALID);
        }

        return result;
    }


}