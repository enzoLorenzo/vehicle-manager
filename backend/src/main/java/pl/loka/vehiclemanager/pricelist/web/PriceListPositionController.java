package pl.loka.vehiclemanager.pricelist.web;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.loka.vehiclemanager.common.Utils;
import pl.loka.vehiclemanager.pricelist.application.port.PriceListPositionUseCase;
import pl.loka.vehiclemanager.pricelist.domain.PriceListPosition;
import pl.loka.vehiclemanager.pricelist.application.port.PriceListPositionUseCase.UpdatePriceListPositionCommand;
import pl.loka.vehiclemanager.pricelist.application.port.PriceListPositionUseCase.CreatePriceListPositionCommand;


import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/price_list")
@AllArgsConstructor
public class PriceListPositionController {

    private PriceListPositionUseCase priceListPositionService;


    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public PriceListPosition getPriceListPositionById(@PathVariable Long id) { return priceListPositionService.findPriceListPositionById(id); }


    @PostMapping
    public ResponseEntity<?> addPriceListPosition (@Valid @RequestBody RestPriceListPositionCommand command){
        PriceListPosition newPriceListPosition = priceListPositionService.addPriceListPosition(command.toCreateCommand());
        return ResponseEntity.created(Utils.createUri(newPriceListPosition)).build();
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void updatePriceListPosition(@PathVariable Long id, @Valid @RequestBody RestPriceListPositionCommand command) {
        priceListPositionService.updatePriceListPosition(command.toUpdateCommand(id));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletPriceListPosition(@NotNull @PathVariable Long id) {
        priceListPositionService.deletePriceListPosition(id);
    }


    @Data
    static class RestPriceListPositionCommand{

        @NotBlank
        private String name;

        @NotBlank
        private String descritpion;

        @NotBlank
        private Double price;

        PriceListPositionUseCase.CreatePriceListPositionCommand toCreateCommand() {
            return new CreatePriceListPositionCommand(name,descritpion,price);
        }

        PriceListPositionUseCase.UpdatePriceListPositionCommand toUpdateCommand(Long id) {
            return new UpdatePriceListPositionCommand(id, name, descritpion, price);
        }
    }
}
