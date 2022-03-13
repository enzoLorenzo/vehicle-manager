package pl.loka.vehiclemanager.user.db;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.loka.vehiclemanager.user.domain.Dealer;

public interface DealerJpaRepository extends JpaRepository<Dealer, Long> {
}
