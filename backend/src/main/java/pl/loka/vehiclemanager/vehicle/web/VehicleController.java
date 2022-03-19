package pl.loka.vehiclemanager.vehicle.web;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.loka.vehiclemanager.common.Utils;
import pl.loka.vehiclemanager.vehicle.application.port.VehicleUseCase;
import pl.loka.vehiclemanager.vehicle.domain.Vehicle;
import pl.loka.vehiclemanager.vehicle.domain.VehicleType;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;

import static pl.loka.vehiclemanager.vehicle.application.port.VehicleUseCase.CreateVehicleCommand;
import static pl.loka.vehiclemanager.vehicle.application.port.VehicleUseCase.UpdateVehicleCommand;

@RestController
@RequestMapping("/vehicle")
@AllArgsConstructor
public class VehicleController {

    private VehicleUseCase vehicleService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Vehicle> getVehicles() {
        return vehicleService.findVehicles();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Vehicle getVehicleById(@PathVariable Long id) {
        return vehicleService.findVehicleById(id);
    }

    @PostMapping
    public ResponseEntity<?> addVehicle(@Valid @RequestBody RestVehicleCommand command) {
        Vehicle newVehicle = vehicleService.addVehicle(command.toCreateCommand());
        return ResponseEntity.created(Utils.createUri(newVehicle)).build();
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void updateVehicle(@PathVariable Long id, @Valid @RequestBody RestVehicleCommand command) {
        vehicleService.updateVehicle(command.toUpdateCommand(id));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteVehicleById(@PathVariable Long id) {
        vehicleService.deleteVehicleById(id);
    }

    @Data
    static class RestVehicleCommand {

        @NotBlank
        private String registration;

        @NotBlank
        private String brand;

        @NotBlank
        private String model;

        private String generation;

        @NotBlank
        private String year;

        @NotBlank
        private String engineCapacity;

        @NotBlank
        private String horsePower;

        private VehicleType type;

        CreateVehicleCommand toCreateCommand() {
            return new CreateVehicleCommand(registration, brand, model, generation, year, engineCapacity, horsePower, type);
        }

        UpdateVehicleCommand toUpdateCommand(Long id) {
            return new UpdateVehicleCommand(id, registration, brand, model, generation, year, engineCapacity, horsePower, type);
        }
    }

}
