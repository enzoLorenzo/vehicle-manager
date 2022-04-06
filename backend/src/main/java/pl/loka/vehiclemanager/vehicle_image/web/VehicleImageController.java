package pl.loka.vehiclemanager.vehicle_image.web;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.loka.vehiclemanager.common.Utils;
import pl.loka.vehiclemanager.vehicle_image.application.port.VehicleImageUseCase;
import pl.loka.vehiclemanager.vehicle_image.domain.VehicleImage;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

import static pl.loka.vehiclemanager.vehicle_image.application.port.VehicleImageUseCase.CreateVehicleImageCommand;
import static pl.loka.vehiclemanager.vehicle_image.application.port.VehicleImageUseCase.CreateVehicleImagesCommand;

@RestController
@RequestMapping("/vehicle-image")
@AllArgsConstructor
public class VehicleImageController {

    private VehicleImageUseCase imageService;

    @GetMapping("/{vehicleId}/image")
    @ResponseStatus(HttpStatus.OK)
    public List<VehicleImage> getVehicleImageByVehicleId(@PathVariable Long vehicleId) {
        return imageService.findImagesByVehicleId(vehicleId);
    }

    @PostMapping("/{vehicleId}/image")
    public ResponseEntity<?> addVehicleImage(
            @PathVariable Long vehicleId,
            @Valid @RequestBody RestVehicleImageCommand command
    ) {
        VehicleImage vehicleImage = imageService.addVehicleImage(command.toCreateCommand(vehicleId));
        return ResponseEntity.created(Utils.createUri(vehicleImage)).build();
    }

    @PostMapping("/{vehicleId}/images")
    @ResponseStatus(HttpStatus.CREATED)
    public void addVehicleImages(
            @PathVariable Long vehicleId,
            @Valid @RequestBody RestVehicleImagesCommand command
    ) {
        imageService.addVehicleImages(command.toCreateCommand(vehicleId));
    }

    @DeleteMapping("/image/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        imageService.deleteVehicleImageById(id);
    }

    @DeleteMapping("/{vehicleId}/images")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteByVehicleId(@PathVariable Long vehicleId) {
        imageService.deleteVehicleImagesByVehicleId(vehicleId);
    }


    @Data
    static class RestVehicleImageCommand {

        @NotNull
        private byte[] image;

        CreateVehicleImageCommand toCreateCommand(Long vehicleId) {
            return new CreateVehicleImageCommand(vehicleId, image);
        }
    }

    @Data
    static class RestVehicleImagesCommand {

        @NotEmpty
        private List<byte[]> images;

        CreateVehicleImagesCommand toCreateCommand(Long vehicleId) {
            return new CreateVehicleImagesCommand(vehicleId, images);
        }
    }
}
