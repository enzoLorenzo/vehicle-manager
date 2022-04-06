package pl.loka.vehiclemanager.vehicle_image.application;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.loka.vehiclemanager.vehicle.application.port.VehicleUseCase;
import pl.loka.vehiclemanager.vehicle_image.application.port.VehicleImageUseCase;
import pl.loka.vehiclemanager.vehicle_image.db.VehicleImageJpaRepository;
import pl.loka.vehiclemanager.vehicle_image.domain.VehicleImage;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class VehicleImageService implements VehicleImageUseCase {

    private final VehicleImageJpaRepository imageRepository;
    private final VehicleUseCase vehicleService;

    @Override
    public List<VehicleImage> findImagesByVehicleId(Long vehicleId) {
        return imageRepository.findVehicleImagesByVehicleId(vehicleId);
    }

    @Override
    @Transactional
    public VehicleImage addVehicleImage(CreateVehicleImageCommand command) {
        isVehicleExist(command.vehicleId());
        VehicleImage newImage = new VehicleImage(command);
        return imageRepository.save(newImage);
    }

    @Override
    @Transactional
    public void addVehicleImages(CreateVehicleImagesCommand command) {
        isVehicleExist(command.vehicleId());
        List<VehicleImage> vehicleImages = command.images().stream()
                .map(image -> new VehicleImage(command.vehicleId(), image))
                .collect(Collectors.toList());
        imageRepository.saveAll(vehicleImages);
    }

    @Override
    @Transactional
    public void deleteVehicleImageById(Long id) {
        VehicleImage image = imageRepository.findById(id)
                .orElseThrow(() -> new VehicleImageNotFoundException("Cannot find a vehicle image with id: " + id));
        imageRepository.delete(image);
    }

    @Override
    @Transactional
    public void deleteVehicleImagesByVehicleId(Long vehicleId) {
        isVehicleExist(vehicleId);
        List<VehicleImage> images = findImagesByVehicleId(vehicleId);
        imageRepository.deleteAll(images);
    }

    private void isVehicleExist(Long vehicleId) {
        vehicleService.findVehicleById(vehicleId);
    }
}
