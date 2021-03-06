package pl.loka.vehiclemanager.workshop.application;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import pl.loka.vehiclemanager.pricelist.db.PriceListPositionJpaRepository;
import pl.loka.vehiclemanager.pricelist.domain.PriceListPosition;
import pl.loka.vehiclemanager.security.application.UserSecurity;
import pl.loka.vehiclemanager.user.application.port.UserUseCase;
import pl.loka.vehiclemanager.user.domain.Dealer;
import pl.loka.vehiclemanager.workshop.application.port.WorkshopUseCase;
import pl.loka.vehiclemanager.workshop.db.WorkshopJpaRepository;
import pl.loka.vehiclemanager.workshop.domain.Workshop;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class WorkshopService implements WorkshopUseCase {

    private final WorkshopJpaRepository repository;
    private final UserUseCase dealerService;
    private final UserSecurity userSecurity;
    private final PriceListPositionJpaRepository priceListPositionJpaRepository;

    public WorkshopService(
            WorkshopJpaRepository repository,
            @Qualifier("dealerService") UserUseCase dealerService,
            UserSecurity userSecurity,
            PriceListPositionJpaRepository priceListPositionJpaRepository) {
        this.repository = repository;
        this.dealerService = dealerService;
        this.userSecurity = userSecurity;
        this.priceListPositionJpaRepository = priceListPositionJpaRepository;
    }

    @Override
    @Transactional
    public List<Workshop> findWorkshops() {
        String username = userSecurity.getLoginUsername();
        Dealer dealer = (Dealer) dealerService.getByUsername(username);
        return dealer.getWorkshops();
    }

    @Override
    public List<Workshop> findAllWorkshops() {
        return repository.findAll();
    }

    @Override
    public Workshop findWorkshopById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new WorkshopNotFoundException("Cannot find a workshop with id: " + id));
    }

    @Override
    @Transactional
    public Workshop addWorkshop(CreateWorkshopCommand command) {
        String username = userSecurity.getLoginUsername();
        Dealer dealer = (Dealer) dealerService.getByUsername(username);
        Workshop newWorkshop = new Workshop(command, dealer);
        return repository.save(newWorkshop);
    }

    @Override
    @Transactional
    public void updateWorkshop(UpdateWorkshopCommand command) {
        Workshop workshop = findWorkshopById(command.id());
        workshop.update(command);
        repository.save(workshop);
    }

    @Override
    public void deleteWorkshopById(Long id) {
        Workshop workshop = findWorkshopById(id);
        repository.delete(workshop);
    }

    @Override
    @Transactional
    public void updatePriceList(Long workshopId, List<PriceListPosition> priceList) {
        Workshop workshop = findWorkshopById(workshopId);
        List<PriceListPosition> oldPriceList = workshop.getPriceList();
        priceListPositionJpaRepository.deleteAll(oldPriceList);
        workshop.setPriceList(priceList);
        repository.save(workshop);
    }
}
