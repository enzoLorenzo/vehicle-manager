package pl.loka.vehiclemanager.task.db;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.loka.vehiclemanager.task.domain.Task;


public interface TaskJpaRepository extends JpaRepository<Task, Long> {

}
