package pl.loka.vehiclemanager.workshop.db;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.loka.vehiclemanager.workshop.domain.Workshop;

public interface WorkshopJpaRepository extends JpaRepository<Workshop, Long> {
}
