package pl.loka.vehiclemanager.vehicle.db;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.loka.vehiclemanager.vehicle.domain.Vehicle;

public interface VehicleJpaRepository extends JpaRepository<Vehicle, Long> {
}
