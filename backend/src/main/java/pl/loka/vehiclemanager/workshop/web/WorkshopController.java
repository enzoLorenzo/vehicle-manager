package pl.loka.vehiclemanager.workshop.web;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.loka.vehiclemanager.common.Utils;
import pl.loka.vehiclemanager.pricelist.application.port.PriceListUseCase;
import pl.loka.vehiclemanager.pricelist.domain.PriceListPosition;
import pl.loka.vehiclemanager.workshop.application.port.WorkshopUseCase;
import pl.loka.vehiclemanager.workshop.application.port.WorkshopUseCase.CreateWorkshopCommand;
import pl.loka.vehiclemanager.workshop.application.port.WorkshopUseCase.UpdateWorkshopCommand;
import pl.loka.vehiclemanager.workshop.domain.Workshop;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;


@RestController
@RequestMapping("/workshop")
@AllArgsConstructor
public class WorkshopController {

    private WorkshopUseCase workshopService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Workshop> getWorkshops() {
        return workshopService.findWorkshops();
    }

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public List<Workshop> getAllWorkshops() {
        return workshopService.findAllWorkshops();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Workshop getWorkshopById(@PathVariable Long id) {
        return workshopService.findWorkshopById(id);
    }


    @PutMapping("/{id}/price-list")
    @ResponseStatus(HttpStatus.OK)
    public void updatePriceListPosition(@NotNull @PathVariable Long id, @Valid @RequestBody List<PriceListPosition> priceList) {
        workshopService.updatePriceList(id, priceList);
    }

    @PostMapping
    public ResponseEntity<?> addWorkshop(@Valid @RequestBody RestWorkshopCommand command) {
        Workshop newWorkshop = workshopService.addWorkshop(command.toCreateCommand());
        return ResponseEntity.created(Utils.createUri(newWorkshop)).build();
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void updateWorkshop(@PathVariable Long id, @Valid @RequestBody RestWorkshopCommand command) {
        workshopService.updateWorkshop(command.toUpdateCommand(id));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteWorkshopById(@PathVariable Long id) {
        workshopService.deleteWorkshopById(id);
    }

    @Data
    static class RestWorkshopCommand {

        @NotBlank
        private String name;

        @NotBlank
        private String address;

        @NotBlank
        private String description;

        CreateWorkshopCommand toCreateCommand() {
            return new CreateWorkshopCommand(name, address, description);
        }

        UpdateWorkshopCommand toUpdateCommand(Long id) {
            return new UpdateWorkshopCommand(id, name, address, description);
        }
    }
}
