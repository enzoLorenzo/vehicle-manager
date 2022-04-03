package pl.loka.vehiclemanager.vehicle_image.db;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.loka.vehiclemanager.vehicle_image.domain.VehicleImage;

import java.util.List;

@Repository
public interface VehicleImageJpaRepository extends JpaRepository<VehicleImage, Long> {

    List<VehicleImage> findVehicleImagesByVehicleId(Long vehicleId);
}
