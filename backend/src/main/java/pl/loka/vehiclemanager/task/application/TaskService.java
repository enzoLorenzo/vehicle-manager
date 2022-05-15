package pl.loka.vehiclemanager.task.application;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import pl.loka.vehiclemanager.security.application.UserSecurity;
import pl.loka.vehiclemanager.task.application.port.TaskUseCase;
import pl.loka.vehiclemanager.task.db.TaskJpaRepository;
import pl.loka.vehiclemanager.task.domain.Task;
import pl.loka.vehiclemanager.user.application.port.UserUseCase;
import pl.loka.vehiclemanager.user.domain.Client;
import pl.loka.vehiclemanager.user.domain.Dealer;
import pl.loka.vehiclemanager.vehicle.domain.Vehicle;
import pl.loka.vehiclemanager.workshop.domain.Workshop;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskService implements TaskUseCase {

    private final TaskJpaRepository repository;
    private final UserSecurity userSecurity;
    private final UserUseCase clientService;
    private final UserUseCase dealerService;

    public TaskService(
            TaskJpaRepository repository,
            UserSecurity userSecurity,
            @Qualifier("clientService") UserUseCase clientService,
            @Qualifier("dealerService") UserUseCase dealerService
    ) {
        this.repository = repository;
        this.userSecurity = userSecurity;
        this.clientService = clientService;
        this.dealerService = dealerService;
    }

    @Override
    public List<Task> findClientTasks() {
        String username = userSecurity.getLoginUsername();
        Client client = (Client) clientService.getByUsername(username);
        return client.getVehicles().stream()
                .map(Vehicle::getTasks)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

    @Override
    public List<Task> findDealerTasks() {
        String username = userSecurity.getLoginUsername();
        Dealer dealer = (Dealer) dealerService.getByUsername(username);
        return dealer.getWorkshops().stream()
                .map(Workshop::getTasks)
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
    public Task findTaskById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException("Cannot find a task with id: " + id));
    }

    @Override
    public Task addTask(Workshop workshop, Vehicle vehicle, CreateTaskCommand command) {
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
