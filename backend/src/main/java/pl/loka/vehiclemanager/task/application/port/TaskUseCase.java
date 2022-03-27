package pl.loka.vehiclemanager.task.application.port;

import pl.loka.vehiclemanager.task.domain.Task;
import pl.loka.vehiclemanager.task.domain.TaskState;
import pl.loka.vehiclemanager.vehicle.domain.Vehicle;
import pl.loka.vehiclemanager.workshop.domain.Workshop;

import java.time.LocalDateTime;
import java.util.List;

public interface TaskUseCase {

    List<Task> findTasks();

    List<Task> findTasksByVehicle(Vehicle vehicle);

    List<Task> findTasksByWorkshop(Workshop workshop);

    Task findTaskById(Long id);

    Task addTask(CreateTaskCommand command);

    void updateTask(UpdateTaskCommand command);

    void deleteTask(Long id);

    record CreateTaskCommand(String description, LocalDateTime startDate,
                             LocalDateTime endDate, TaskState taskState){
    }


    record UpdateTaskCommand(Long id, String description, LocalDateTime startDate,
                             LocalDateTime endDate, TaskState taskState){
    }






}
