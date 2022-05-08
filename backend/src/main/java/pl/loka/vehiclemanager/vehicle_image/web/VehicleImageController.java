package pl.loka.vehiclemanager.vehicle_image.web;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.loka.vehiclemanager.vehicle.application.port.VehicleUseCase;
import pl.loka.vehiclemanager.vehicle_image.application.ImageUtility;
import pl.loka.vehiclemanager.vehicle_image.db.VehicleImageJpaRepository;
import pl.loka.vehiclemanager.vehicle_image.domain.VehicleImage;

import java.io.IOException;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/vehicle-image")
@AllArgsConstructor
public class VehicleImageController {

    private VehicleImageJpaRepository imageJpaRepository;
    private VehicleUseCase vehicleService;

    @PostMapping("/{vehicleId}/upload")
    public Long uploadImage(
            @PathVariable Long vehicleId,
            @RequestParam("imageFile") MultipartFile file) throws IOException {
        log.info("Original Image Byte Size - " + file.getBytes().length);
        VehicleImage img = VehicleImage.builder()
                .vehicleId(vehicleId)
                .name(file.getOriginalFilename())
                .type(file.getContentType())
                .image(ImageUtility.compressBytes(file.getBytes()))
                .build();
        Long imageId = imageJpaRepository.save(img).getId();
        vehicleService.updateImage(vehicleId, imageId);
        return imageId;
    }

    @GetMapping("/download/{imageId}")
    public VehicleImage getImage(@PathVariable Long imageId) throws IOException {
        final Optional<VehicleImage> retrievedImage = imageJpaRepository.findById(imageId);
        VehicleImage vehicleImage = retrievedImage.get();
        return VehicleImage.builder()
                .vehicleId(vehicleImage.getVehicleId())
                .name(vehicleImage.getName())
                .type(vehicleImage.getType())
                .image(ImageUtility.decompressImage(vehicleImage.getImage()))
                .build();
    }

    @DeleteMapping("/{imageId}")
    public void deleteImage(@PathVariable Long imageId){
        VehicleImage image = imageJpaRepository.findById(imageId).get();
        vehicleService.deleteImage(image.getVehicleId());
        imageJpaRepository.delete(image);
    }


}
