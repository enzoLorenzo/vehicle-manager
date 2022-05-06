package pl.loka.vehiclemanager.task_rating.application;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.loka.vehiclemanager.task.application.port.TaskUseCase;
import pl.loka.vehiclemanager.task.domain.Task;
import pl.loka.vehiclemanager.task_rating.application.port.TaskRatingUseCase;
import pl.loka.vehiclemanager.task_rating.db.TaskRatingJpaRepository;
import pl.loka.vehiclemanager.task_rating.domain.TaskRating;

import javax.transaction.Transactional;

@Service
@AllArgsConstructor
public class TaskRatingService implements TaskRatingUseCase {
    private final TaskRatingJpaRepository repository;
    private final TaskUseCase taskService;

    @Override
    public TaskRating findTaskRatingByTaskId(Long taskId) {
        return repository.findTaskRatingByTaskId(taskId)
                .orElseThrow(() -> new TaskRatingNotFoundException("Task with id " + taskId + " has no rating"));
    }

    @Override
    public TaskRating findTaskRatingById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new TaskRatingNotFoundException("Cannot find a task with id " + id));

    }

    @Override
    @Transactional
    public TaskRating addTaskRating(CreateTaskRatingCommand command) {
        Task task = taskService.findTaskById(command.taskId());
        TaskRating newTaskRating = new TaskRating(command, task);
        return repository.save(newTaskRating);
    }

    @Override
    @Transactional
    public void updateTaskRating(UpdateTaskRatingCommand command) {
        TaskRating taskRating = findTaskRatingById(command.id());
        taskRating.update(command);
        repository.save(taskRating);
    }

    @Override
    @Transactional
    public void deleteTaskRating(Long id) {
        TaskRating taskRating = findTaskRatingById(id);
        repository.delete(taskRating);
    }
}
