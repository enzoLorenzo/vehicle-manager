package pl.loka.vehiclemanager.vehicle.application;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import pl.loka.vehiclemanager.security.application.UserSecurity;
import pl.loka.vehiclemanager.user.application.port.UserUseCase;
import pl.loka.vehiclemanager.user.domain.Client;
import pl.loka.vehiclemanager.vehicle.application.port.VehicleUseCase;
import pl.loka.vehiclemanager.vehicle.db.VehicleJpaRepository;
import pl.loka.vehiclemanager.vehicle.domain.Vehicle;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class VehicleService implements VehicleUseCase {

    private final VehicleJpaRepository repository;
    private final UserUseCase clientService;
    private final UserSecurity userSecurity;

    public VehicleService(
            VehicleJpaRepository repository,
            @Qualifier("clientService") UserUseCase clientService,
            UserSecurity userSecurity
    ) {
        this.repository = repository;
        this.clientService = clientService;
        this.userSecurity = userSecurity;
    }

    @Override
    public List<Vehicle> findVehicles() {
        String username = userSecurity.getLoginUsername();
        Client client = (Client) clientService.getByUsername(username);
        return client.getVehicles();
    }

    @Override
    public Vehicle findVehicleById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new VehicleNotFoundException("Cannot find a vehicle with id: " + id));
    }

    @Override
    @Transactional
    public Vehicle addVehicle(CreateVehicleCommand command) {
        String username = userSecurity.getLoginUsername();
        Client owner = (Client) clientService.getByUsername(username);
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
    public void updateImage(Long vehicleId, Long imageId) {
        Vehicle vehicle = findVehicleById(vehicleId);
        vehicle.updateImage(imageId);
        repository.save(vehicle);
    }

    @Override
    @Transactional
    public void deleteImage(Long vehicleId) {
        Vehicle vehicle = findVehicleById(vehicleId);
        vehicle.deleteImage();
        repository.save(vehicle);
    }

    @Override
    @Transactional
    public void deleteVehicleById(Long id) {
        Vehicle vehicle = findVehicleById(id);
        repository.delete(vehicle);
    }
}
