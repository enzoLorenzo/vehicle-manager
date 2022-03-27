package pl.loka.vehiclemanager.task.application;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.loka.vehiclemanager.task.application.port.TaskUseCase;
import pl.loka.vehiclemanager.task.db.TaskJpaRepository;
import pl.loka.vehiclemanager.task.domain.Task;
import pl.loka.vehiclemanager.vehicle.db.VehicleJpaRepository;
import pl.loka.vehiclemanager.vehicle.domain.Vehicle;
import pl.loka.vehiclemanager.workshop.db.WorkshopJpaRepository;
import pl.loka.vehiclemanager.workshop.domain.Workshop;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TaskService implements TaskUseCase {

    private final TaskJpaRepository repository;
    private final WorkshopJpaRepository workshopRepository;
    private final VehicleJpaRepository vehicleRepository;

    @Override
    public List<Task> findTasks() {
        return repository.findAll();
    }

    @Override
    public List<Task> findTasksByVehicle(Vehicle vehicle) {
        return repository.findAll()
                .stream()
                .filter(task -> task.getVehicle().equals(vehicle))
                .collect(Collectors.toList());
    }

    @Override
    public List<Task> findTasksByWorkshop(Workshop workshop) {
        return repository.findAll()
                .stream()
                .filter(task -> task.getWorkshop().equals(workshop))
                .collect(Collectors.toList());
    }

    @Override
    public Task findTaskById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException("Cannot find a task with id: " + id));
    }

    @Override
    public Task addTask(CreateTaskCommand command) {
        Workshop workshop = workshopRepository.findAll().get(0);
        Vehicle vehicle = vehicleRepository.findAll().get(0);
        Task newTask = new Task(command, vehicle, workshop);
        return repository.save(newTask);
    }

    @Override
    public void updateTask(UpdateTaskCommand command) {
        Task task = findTaskById(command.id());
        task.update(command);
        repository.save(task);
    }

    @Override
    public void deleteTask(Long id) {
        Task task = findTaskById(id);
        repository.delete(task);
    }
}
