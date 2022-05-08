package pl.loka.vehiclemanager.vehicle_image.db;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.loka.vehiclemanager.vehicle_image.domain.VehicleImage;

import java.util.List;
import java.util.Optional;

@Repository
public interface VehicleImageJpaRepository extends JpaRepository<VehicleImage, Long> {

    List<VehicleImage> findVehicleImagesByVehicleId(Long vehicleId);

    Optional<VehicleImage> findVehicleImageByName(String name);
}
