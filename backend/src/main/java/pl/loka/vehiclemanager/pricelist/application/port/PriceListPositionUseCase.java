package pl.loka.vehiclemanager.pricelist.application.port;

import pl.loka.vehiclemanager.pricelist.domain.PriceListPosition;

import java.util.List;

public interface PriceListPositionUseCase {

    List<PriceListPosition> findPriceListPositionsByWorkshop(Long workshopId);

    PriceListPosition findPriceListPositionById(Long id);

    PriceListPosition addPriceListPosition(CreatePriceListPositionCommand command);

    void updatePriceListPosition(UpdatePriceListPositionCommand command);

    void deletePriceListPosition(Long id);

    record CreatePriceListPositionCommand(String name, String description, Double price){

    }

    record UpdatePriceListPositionCommand(Long id, String name, String description, Double price){

    }


}