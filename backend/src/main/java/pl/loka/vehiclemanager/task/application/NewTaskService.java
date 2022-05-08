package pl.loka.vehiclemanager.task.application;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.loka.vehiclemanager.task.application.port.TaskUseCase;
import pl.loka.vehiclemanager.task.domain.Task;
import pl.loka.vehiclemanager.vehicle.application.port.VehicleUseCase;
import pl.loka.vehiclemanager.vehicle.domain.Vehicle;
import pl.loka.vehiclemanager.workshop.application.port.WorkshopUseCase;
import pl.loka.vehiclemanager.workshop.domain.Workshop;

@Service
@AllArgsConstructor
public class NewTaskService {

    private final WorkshopUseCase workshopService;
    private final VehicleUseCase vehicleService;
    private final TaskUseCase taskService;

    public Task addTask(TaskUseCase.CreateTaskCommand command) {
        Workshop workshop = workshopService.findWorkshopById(command.workshopId());
        Vehicle vehicle = vehicleService.findVehicleById(command.vehicleId());
        return taskService.addTask(workshop, vehicle, command);
    }
}
