package pl.loka.vehiclemanager.user.db;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.loka.vehiclemanager.user.domain.Dealer;

import java.util.Optional;

@Repository
public interface DealerJpaRepository extends JpaRepository<Dealer, Long> {

    Optional<Dealer> findByUsernameIgnoreCase(String username);

}
