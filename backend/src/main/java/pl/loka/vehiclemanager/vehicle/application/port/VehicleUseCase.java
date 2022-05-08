package pl.loka.vehiclemanager.vehicle.application.port;

import pl.loka.vehiclemanager.vehicle.domain.Vehicle;
import pl.loka.vehiclemanager.vehicle.domain.VehicleType;

import javax.transaction.Transactional;
import java.util.List;

public interface VehicleUseCase {

    List<Vehicle> findVehicles();

    Vehicle findVehicleById(Long id);

    Vehicle addVehicle(CreateVehicleCommand command);

    void updateVehicle(UpdateVehicleCommand command);

    @Transactional
    void updateImage(Long vehicleId, Long imageId);

    void deleteVehicleById(Long id);

    void deleteImage(Long vehicleId);

    record CreateVehicleCommand(String registration, String brand, String model,
                                String generation, String year, String engineCapacity,
                                String horsePower, VehicleType type) {
    }

    record UpdateVehicleCommand(Long id, String registration, String brand, String model,
                                String generation, String year, String engineCapacity,
                                String horsePower, VehicleType type) {
    }
}
