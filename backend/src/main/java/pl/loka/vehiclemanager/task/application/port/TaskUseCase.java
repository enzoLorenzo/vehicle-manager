package pl.loka.vehiclemanager.task.application.port;

import pl.loka.vehiclemanager.task.domain.Task;
import pl.loka.vehiclemanager.task.domain.TaskStatus;
import pl.loka.vehiclemanager.vehicle.domain.Vehicle;
import pl.loka.vehiclemanager.workshop.domain.Workshop;

import java.time.LocalDateTime;
import java.util.List;

public interface TaskUseCase {

    List<Task> findClientTasks();

    List<Task> findDealerTasks();

    List<Task> findTasksByVehicle(Long vehicleId);

    Task findTaskById(Long id);

    Task addTask(Workshop workshop, Vehicle vehicle, CreateTaskCommand command);

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
