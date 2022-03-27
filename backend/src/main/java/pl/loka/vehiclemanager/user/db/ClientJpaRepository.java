package pl.loka.vehiclemanager.user.db;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.loka.vehiclemanager.user.domain.Client;

import java.util.Optional;

@Repository
public interface ClientJpaRepository extends JpaRepository<Client, Long> {

    Optional<Client> findByUsernameIgnoreCase(String username);
}
