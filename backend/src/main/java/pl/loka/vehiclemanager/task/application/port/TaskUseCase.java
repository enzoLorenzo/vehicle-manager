package pl.loka.vehiclemanager.task.application.port;

import pl.loka.vehiclemanager.task.domain.Task;
import pl.loka.vehiclemanager.task.domain.TaskStatus;

import java.time.LocalDateTime;
import java.util.List;

public interface TaskUseCase {

    List<Task> findTasks();

    List<Task> findTasksByVehicle(Long vehicleId);

    List<Task> findTasksByWorkshop(Long workshopId);

    Task findTaskById(Long id);

    Task addTask(CreateTaskCommand command);

    void updateTask(UpdateTaskCommand command);

    void updateTaskStatus(UpdateStatusCommand command);

    void deleteTask(Long id);

    record CreateTaskCommand(String description, LocalDateTime startDate,
                             LocalDateTime endDate, TaskStatus taskStatus, Long vehicleId, Long workshopId) {
    }


    record UpdateTaskCommand(Long id, String description, LocalDateTime startDate,
                             LocalDateTime endDate, TaskStatus taskStatus, Long vehicleId, Long workshopId) {
    }

    record UpdateStatusCommand(Long id, TaskStatus status) {
    }

}
