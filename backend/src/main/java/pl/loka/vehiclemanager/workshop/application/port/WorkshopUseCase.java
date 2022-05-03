package pl.loka.vehiclemanager.workshop.application.port;

import pl.loka.vehiclemanager.workshop.domain.ProvidedService;
import pl.loka.vehiclemanager.workshop.domain.Workshop;

import java.util.List;

public interface WorkshopUseCase {

    List<Workshop> findWorkshops();

    Workshop findWorkshopById(Long id);

    Workshop addWorkshop(CreateWorkshopCommand command);

    void updateWorkshop(UpdateWorkshopCommand command);

    void deleteWorkshopById(Long id);


    record CreateWorkshopCommand(String name, String address, String description,
                                 List<ProvidedService> providedServices){
    }

    record UpdateWorkshopCommand(Long id, String name, String address, String description,
                                 List<ProvidedService> providedServices){
    }


}
