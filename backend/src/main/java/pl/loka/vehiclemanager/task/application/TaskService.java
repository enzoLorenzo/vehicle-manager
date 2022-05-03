package pl.loka.vehiclemanager.task.application;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import pl.loka.vehiclemanager.security.application.UserSecurity;
import pl.loka.vehiclemanager.task.application.port.TaskUseCase;
import pl.loka.vehiclemanager.task.db.TaskJpaRepository;
import pl.loka.vehiclemanager.task.domain.Task;
import pl.loka.vehiclemanager.user.application.port.UserUseCase;
import pl.loka.vehiclemanager.user.domain.Client;
import pl.loka.vehiclemanager.vehicle.application.port.VehicleUseCase;
import pl.loka.vehiclemanager.vehicle.db.VehicleJpaRepository;
import pl.loka.vehiclemanager.vehicle.domain.Vehicle;
import pl.loka.vehiclemanager.workshop.application.port.WorkshopUseCase;
import pl.loka.vehiclemanager.workshop.db.WorkshopJpaRepository;
import pl.loka.vehiclemanager.workshop.domain.Workshop;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskService implements TaskUseCase {

    private final TaskJpaRepository repository;
    private final WorkshopUseCase workshopService;
    private final VehicleUseCase vehicleService;
    private final UserSecurity userSecurity;
    private final UserUseCase clientService;

    public TaskService(
            TaskJpaRepository repository,
            WorkshopUseCase workshopService,
            VehicleUseCase vehicleService,
            UserSecurity userSecurity,
            @Qualifier("clientService") UserUseCase clientService
    ) {
        this.repository = repository;
        this.workshopService = workshopService;
        this.vehicleService = vehicleService;
        this.userSecurity = userSecurity;
        this.clientService = clientService;
    }

    @Override
    public List<Task> findTasks() {
        String username = userSecurity.getLoginUsername();
        Client client = (Client) clientService.getByUsername(username);
        return client.getVehicles().stream()
                .map(Vehicle::getTasks)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

    @Override
    public List<Task> findTasksByVehicle(Long vehicleId) {
        return repository.findAll()
                .stream()
                .filter(task -> task.getVehicle().getId().equals(vehicleId))
                .collect(Collectors.toList());
    }

    @Override
    public List<Task> findTasksByWorkshop(Long workshopId) {
        return repository.findAll()
                .stream()
                .filter(task -> task.getWorkshop().getId().equals(workshopId))
                .collect(Collectors.toList());
    }

    @Override
    public Task findTaskById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException("Cannot find a task with id: " + id));
    }

    @Override
    public Task addTask(CreateTaskCommand command) {
        Workshop workshop = workshopService.findWorkshopById(command.workshopId());
        Vehicle vehicle = vehicleService.findVehicleById(command.vehicleId());
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
    public void updateTaskStatus(UpdateStatusCommand command) {
        findTaskById(command.id()).updateStatus(command.status());
    }

    @Override
    public void deleteTask(Long id) {
        Task task = findTaskById(id);
        repository.delete(task);
    }
}
