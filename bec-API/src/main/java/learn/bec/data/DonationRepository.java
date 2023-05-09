package learn.bec.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import learn.bec.models.Donation;
import jakarta.transaction.Transactional;

@Repository
public interface DonationRepository extends JpaRepository<Donation, Integer> {

    @Override
    @Transactional
    <S extends Donation> S save(S entity);

    default Donation add(Donation donation) {
        return save(donation);
    }
}
