package pl.loka.vehiclemanager.pricelist.application;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.loka.vehiclemanager.pricelist.application.port.PriceListPositionUseCase;
import pl.loka.vehiclemanager.pricelist.db.PriceListPositionJpaRepository;
import pl.loka.vehiclemanager.pricelist.domain.PriceListPosition;
import pl.loka.vehiclemanager.workshop.db.WorkshopJpaRepository;
import pl.loka.vehiclemanager.workshop.domain.Workshop;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PriceListPositionService implements PriceListPositionUseCase {
    private final PriceListPositionJpaRepository repository;
    private final WorkshopJpaRepository workshopRepository;


    @Override
    public List<PriceListPosition> findPriceListPositionsByWorkshop(Long workshopId) {
        return repository.findAll()
                .stream()
                .filter(priceListPosition -> priceListPosition.getWorkshop().getId().equals(workshopId))
                .collect(Collectors.toList());
    }

    @Override
    public PriceListPosition findPriceListPositionById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new PriceListPositionNotFoundException("Cannot find a price list position with id: " + id));
    }

    @Override
    public PriceListPosition addPriceListPosition(CreatePriceListPositionCommand command) {
        Workshop workshop = workshopRepository.findAll().get(0);
        PriceListPosition newPosition = new PriceListPosition(command,workshop);
        return repository.save(newPosition);
    }

    @Override
    public void updatePriceListPosition(UpdatePriceListPositionCommand command) {
        PriceListPosition priceListPosition = findPriceListPositionById(command.id());
        priceListPosition.update(command);
        repository.save(priceListPosition);
    }

    @Override
    public void deletePriceListPosition(Long id) {
        PriceListPosition priceListPosition = findPriceListPositionById(id);
        repository.delete(priceListPosition);
    }
}
