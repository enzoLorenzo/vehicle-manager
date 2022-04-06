package pl.loka.vehiclemanager.repair_rating.db;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.loka.vehiclemanager.repair_rating.domain.RepairRating;

import java.util.List;

public interface RepairRatingJpaRepository extends JpaRepository<RepairRating, Long> {

    List<RepairRating> findRepairRatingByTaskId(Long taskId);
}
