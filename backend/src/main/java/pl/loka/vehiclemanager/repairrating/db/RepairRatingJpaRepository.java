package pl.loka.vehiclemanager.repairrating.db;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.loka.vehiclemanager.repairrating.domain.RepairRating;

public interface RepairRatingJpaRepository extends JpaRepository<RepairRating, Long> {

}
