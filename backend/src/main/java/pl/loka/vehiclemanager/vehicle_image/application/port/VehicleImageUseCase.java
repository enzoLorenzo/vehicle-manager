package pl.loka.vehiclemanager.vehicle_image.application.port;

import pl.loka.vehiclemanager.vehicle_image.domain.VehicleImage;

import java.util.List;

public interface VehicleImageUseCase {

    List<VehicleImage> findImagesByVehicleId(Long vehicleId);

    VehicleImage addVehicleImage(CreateVehicleImageCommand command);

    void addVehicleImages(CreateVehicleImagesCommand command);

    void deleteVehicleImageById(Long id);

    void deleteVehicleImagesByVehicleId(Long vehicleId);

    record CreateVehicleImageCommand(Long vehicleId, byte[] image){

    }
    record CreateVehicleImagesCommand(Long vehicleId, List<byte[]> images){

    }

}
