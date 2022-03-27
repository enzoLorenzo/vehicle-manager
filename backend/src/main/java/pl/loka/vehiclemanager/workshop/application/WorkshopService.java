package pl.loka.vehiclemanager.workshop.application;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.loka.vehiclemanager.user.db.DealerJpaRepository;
import pl.loka.vehiclemanager.user.domain.Dealer;
import pl.loka.vehiclemanager.workshop.application.port.WorkshopUseCase;
import pl.loka.vehiclemanager.workshop.db.WorkshopJpaRepository;
import pl.loka.vehiclemanager.workshop.domain.Workshop;

import javax.transaction.Transactional;
import java.util.List;

@Service
@AllArgsConstructor
public class WorkshopService implements WorkshopUseCase {

    private final WorkshopJpaRepository repository;
    private final DealerJpaRepository dealerRepository;


    @Override
    public List<Workshop> findWorkshops() {
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
        Dealer dealer = dealerRepository.findAll().get(0);
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
