package learn.bec.data;


import learn.bec.models.MemberUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberUserRepository extends JpaRepository<MemberUser, Integer> {
    MemberUser findByUsername(String username);
    MemberUser findByEmail(String email);
}
