package pl.loka.vehiclemanager.user.db;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.loka.vehiclemanager.user.domain.Client;

public interface ClientJpaRepository extends JpaRepository<Client, Long> {
}
