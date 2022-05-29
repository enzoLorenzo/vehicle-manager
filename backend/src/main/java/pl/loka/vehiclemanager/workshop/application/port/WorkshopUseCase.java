package pl.loka.vehiclemanager.workshop.application.port;

import pl.loka.vehiclemanager.pricelist.domain.PriceListPosition;
import pl.loka.vehiclemanager.workshop.domain.Workshop;

import java.util.List;

public interface WorkshopUseCase {

    List<Workshop> findWorkshops();

    List<Workshop> findAllWorkshops();

    Workshop findWorkshopById(Long id);

    Workshop addWorkshop(CreateWorkshopCommand command);

    void updateWorkshop(UpdateWorkshopCommand command);

    void deleteWorkshopById(Long id);

    void updatePriceList(Long workshopId, List<PriceListPosition> priceList);

    record CreateWorkshopCommand(String name, String address, String description) {
    }

    record UpdateWorkshopCommand(Long id, String name, String address, String description) {
    }

    record PriceListPositionDTO(String name, String description, Double price) {
    }

}
