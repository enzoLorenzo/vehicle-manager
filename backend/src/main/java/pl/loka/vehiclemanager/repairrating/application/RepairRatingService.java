package pl.loka.vehiclemanager.repairrating.application;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.loka.vehiclemanager.repairrating.application.port.RapairRatingUseCase;
import pl.loka.vehiclemanager.repairrating.db.RepairRatingJpaRepository;
import pl.loka.vehiclemanager.repairrating.domain.RepairRating;
import pl.loka.vehiclemanager.task.db.TaskJpaRepository;
import pl.loka.vehiclemanager.task.domain.Task;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class RepairRatingService implements RapairRatingUseCase {
    private final RepairRatingJpaRepository repository;
    private final TaskJpaRepository taskRepository;


    @Override
    public List<RepairRating> findRepairRatings() {
        return repository.findAll();
    }

    @Override
    public List<RepairRating> findRepairRatingByTask(Long TaskId) {
        return repository.findAll()
                .stream()
                .filter(repairRating -> repairRating.getTask().getId().equals(TaskId))
                .collect(Collectors.toList());
    }

    @Override
    public RepairRating findRepairRatingById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RepairRatingNotFoundException("Cannot find a task with id " + id));

    }

    @Override
    public RepairRating addRepairRating(CreateRepairRatingCommand command) {
        Task task = taskRepository.findAll().get(0);
        RepairRating newRepairRating = new RepairRating(command, task);
        return repository.save(newRepairRating);
    }

    @Override
    public void updateRepairRating(UpdateRepairRatingCommand command) {
        RepairRating repairRating = findRepairRatingById(command.id());
        repairRating.update(command);
        repository.save(repairRating);
    }

    @Override
    public void deleteRepairRating(Long id) {
        RepairRating repairRating = findRepairRatingById(id);
        repository.delete(repairRating);
    }
}
