package pl.loka.vehiclemanager.task_rating.db;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.loka.vehiclemanager.task_rating.domain.TaskRating;

import java.util.List;
import java.util.Optional;

public interface TaskRatingJpaRepository extends JpaRepository<TaskRating, Long> {

    Optional<TaskRating> findTaskRatingByTaskId(Long taskId);
}
