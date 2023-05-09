package learn.bec.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.*;

@Entity
@Data
@NoArgsConstructor
public class MemberUser implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int memberUserId;
    @NotNull
    @NotBlank
    private String username;
    @NotNull
    @NotBlank
    @Column(name = "password_hash")
    private String password;
    private boolean enabled;
    @NotNull
    @NotBlank
    private String firstName;
    @NotNull
    @NotBlank
    private String lastName;
    @NotNull
    @NotBlank
    @Email
    private String email;
    @NotNull
    private LocalDate dob;

    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(name = "member_user_role",
            joinColumns = @JoinColumn(name = "member_user_id"),
            inverseJoinColumns = @JoinColumn(name = "app_role_id")
    )
    private Set<AppRole> roles = new HashSet<>();
    @OneToMany(mappedBy = "memberUser", cascade = CascadeType.ALL)
    private Set<Donation> donations = new HashSet<>();

    @Override
    public String getUsername() {
        return username;
    }


    @Override
    public String getPassword() {
        return password;
    }
    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    public void addAuthorities(Collection<String> authorityNames) {
        roles.clear();
        for (String name : authorityNames) {
            AppRole role = new AppRole();
            role.setRoleName(name);
            roles.add(role);
        }
    }
}
