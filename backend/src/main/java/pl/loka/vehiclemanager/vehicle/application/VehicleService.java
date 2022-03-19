package pl.loka.vehiclemanager.vehicle.application;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.loka.vehiclemanager.user.db.ClientJpaRepository;
import pl.loka.vehiclemanager.user.domain.Client;
import pl.loka.vehiclemanager.vehicle.application.port.VehicleUseCase;
import pl.loka.vehiclemanager.vehicle.db.VehicleJpaRepository;
import pl.loka.vehiclemanager.vehicle.domain.Vehicle;

import javax.transaction.Transactional;
import java.util.List;

@Service
@AllArgsConstructor
public class VehicleService implements VehicleUseCase {

    private final VehicleJpaRepository repository;
    private final ClientJpaRepository clientRepository;

    @Override
    public List<Vehicle> findVehicles() {
        return repository.findAll();
    }

    @Override
    public Vehicle findVehicleById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new VehicleNotFoundException("Cannot find a vehicle with id: " + id));
    }

    @Override
    @Transactional
    public Vehicle addVehicle(CreateVehicleCommand command) {
        Client owner = clientRepository.findAll().get(0);
        Vehicle newVehicle = new Vehicle(command, owner);
        return repository.save(newVehicle);
    }

    @Override
    @Transactional
    public void updateVehicle(UpdateVehicleCommand command) {
        Vehicle vehicle = findVehicleById(command.id());
        vehicle.update(command);
        repository.save(vehicle);
    }

    @Override
    @Transactional
    public void deleteVehicleById(Long id) {
        Vehicle vehicle = findVehicleById(id);
        repository.delete(vehicle);
    }
}
