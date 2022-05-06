package pl.loka.vehiclemanager.pricelist.application;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.loka.vehiclemanager.pricelist.application.port.PriceListPositionUseCase;
import pl.loka.vehiclemanager.pricelist.db.PriceListPositionJpaRepository;
import pl.loka.vehiclemanager.pricelist.domain.PriceListPosition;
import pl.loka.vehiclemanager.workshop.application.port.WorkshopUseCase;
import pl.loka.vehiclemanager.workshop.db.WorkshopJpaRepository;
import pl.loka.vehiclemanager.workshop.domain.Workshop;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PriceListPositionService implements PriceListPositionUseCase {
    private final PriceListPositionJpaRepository repository;
    private final WorkshopUseCase workshopService;


    @Override
    public List<PriceListPosition> findPriceListPositionsByWorkshopId(Long workshopId) {
        return repository.findPriceListPositionByWorkshop_Id(workshopId);
    }

    @Override
    public PriceListPosition findPriceListPositionById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new PriceListPositionNotFoundException("Cannot find a price list position with id: " + id));
    }

    @Override
    @Transactional
    public PriceListPosition addPriceListPosition(CreatePriceListPositionCommand command) {
        Workshop workshop = workshopService.findWorkshopById(command.workshopId());
        PriceListPosition newPosition = new PriceListPosition(command,workshop);
        return repository.save(newPosition);
    }

    @Override
    @Transactional
    public void updatePriceListPosition(UpdatePriceListPositionCommand command) {
        PriceListPosition priceListPosition = findPriceListPositionById(command.id());
        priceListPosition.update(command);
        repository.save(priceListPosition);
    }

    @Override
    @Transactional
    public void deletePriceListPosition(Long id) {
        PriceListPosition priceListPosition = findPriceListPositionById(id);
        repository.delete(priceListPosition);
    }
}
