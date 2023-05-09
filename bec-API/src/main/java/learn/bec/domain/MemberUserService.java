package learn.bec.domain;


import learn.bec.data.MemberUserRepository;
import learn.bec.models.MemberUser;
import learn.bec.validation.Result;
import learn.bec.validation.ResultType;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class MemberUserService implements UserDetailsService {

    private final MemberUserRepository memberUserRepository;


    public MemberUserService(MemberUserRepository memberUserRepository) {
        this.memberUserRepository = memberUserRepository;
    }


    public List<MemberUser> findAll() {return memberUserRepository.findAll(); }

    public MemberUser findById(int memberUserId) {return memberUserRepository.findById(memberUserId).orElse(null);}

    public Result<MemberUser> save(MemberUser user) {
        Result<MemberUser> result = validate(user);
        if(!result.isSuccess()) {
            return result;
        }

        MemberUser payload = memberUserRepository.save(user);
        result.setPayload(payload);
        return result;

    }

    public boolean deleteById(int memberUserId) {
        if (memberUserId <= 0) {
            return false;
        }
        if (memberUserRepository.findById(memberUserId).isEmpty()) {
            return false;
        }

        memberUserRepository.deleteById(memberUserId);
        return true;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        MemberUser user = memberUserRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(String.format("%s not found", username));
        }
        return user;
    }

    private Result<MemberUser> validate(MemberUser user) {
        Result<MemberUser> result = new Result<>();

        if (user == null) {
            result.addErr("", "user cannot be null", ResultType.INVALID);
            return result;
        }

        if (user.getMemberUserId() > 0) {
            if (!memberUserRepository.existsById(user.getMemberUserId())) {
                result.addErr("", "not found", ResultType.NOT_FOUND);
                return result;
            }
        }

        if(Validations.isNullOrBlank(user.getUsername())) {
            result.addErr("", "username is required", ResultType.INVALID);
        }
        if(memberUserRepository.findByUsername(user.getUsername()) != null) {
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
        if(memberUserRepository.findByEmail(user.getEmail()) != null) {
            result.addErr("", "an account with this email already exists", ResultType.ALREADY_EXISTS);
        }

        if(user.getDob().isAfter(LocalDate.now())) {
            result.addErr("", "date of birth cannot be in the future", ResultType.INVALID);
        }

        return result;
    }
}
