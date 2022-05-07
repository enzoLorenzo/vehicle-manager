package pl.loka.vehiclemanager.workshop.application;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import pl.loka.vehiclemanager.security.application.UserSecurity;
import pl.loka.vehiclemanager.task.application.port.TaskUseCase;
import pl.loka.vehiclemanager.user.application.port.UserUseCase;
import pl.loka.vehiclemanager.user.domain.Dealer;
import pl.loka.vehiclemanager.workshop.application.port.WorkshopUseCase;
import pl.loka.vehiclemanager.workshop.db.WorkshopJpaRepository;
import pl.loka.vehiclemanager.workshop.domain.Workshop;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class WorkshopService implements WorkshopUseCase {

    private final WorkshopJpaRepository repository;
    private final UserUseCase dealerService;
    private final UserSecurity userSecurity;

    public WorkshopService(
            WorkshopJpaRepository repository,
            @Qualifier("dealerService") UserUseCase dealerService,
            UserSecurity userSecurity) {
        this.repository = repository;
        this.dealerService = dealerService;
        this.userSecurity = userSecurity;
    }

    @Override
    @Transactional
    public List<Workshop> findWorkshops() {
        String username = userSecurity.getLoginUsername();
        Dealer dealer = (Dealer) dealerService.getByUsername(username);
        return dealer.getWorkshops();
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
}
