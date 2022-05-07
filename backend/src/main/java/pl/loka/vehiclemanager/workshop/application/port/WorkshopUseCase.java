package pl.loka.vehiclemanager.workshop.application.port;

import pl.loka.vehiclemanager.task.application.port.TaskUseCase;
import pl.loka.vehiclemanager.task.domain.Task;
import pl.loka.vehiclemanager.workshop.domain.Workshop;

import java.util.List;

public interface WorkshopUseCase {

    List<Workshop> findWorkshops();

    Workshop findWorkshopById(Long id);

    Workshop addWorkshop(CreateWorkshopCommand command);

    void updateWorkshop(UpdateWorkshopCommand command);

    void deleteWorkshopById(Long id);


    record CreateWorkshopCommand(String name, String address, String description){
    }

    record UpdateWorkshopCommand(Long id, String name, String address, String description){
    }


}
