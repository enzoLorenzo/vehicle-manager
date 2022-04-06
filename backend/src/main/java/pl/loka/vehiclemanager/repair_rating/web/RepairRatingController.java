package pl.loka.vehiclemanager.repair_rating.web;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.loka.vehiclemanager.common.Utils;
import pl.loka.vehiclemanager.repair_rating.application.port.RapairRatingUseCase;
import pl.loka.vehiclemanager.repair_rating.domain.Rating;
import pl.loka.vehiclemanager.repair_rating.domain.RepairRating;
import pl.loka.vehiclemanager.repair_rating.application.port.RapairRatingUseCase.UpdateRepairRatingCommand;
import pl.loka.vehiclemanager.repair_rating.application.port.RapairRatingUseCase.CreateRepairRatingCommand;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("/repair_rating")
@AllArgsConstructor
public class RepairRatingController {
    
    private RapairRatingUseCase repairRatingService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<RepairRating> getRepairRatings() { return repairRatingService.findRepairRatings(); }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public RepairRating getRepairRatingById(@NotNull @PathVariable Long id) { return repairRatingService.findRepairRatingById(id); }

    @PostMapping
    public ResponseEntity<?> addRepairRating (@Valid @RequestBody RestRepairRatingCommand command){
        RepairRating newRepairRating = repairRatingService.addRepairRating(command.toCreateCommand());
        return ResponseEntity.created(Utils.createUri(newRepairRating)).build();
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void updateRepairRating(@NotNull @PathVariable Long id, @Valid @RequestBody RestRepairRatingCommand command) {
        repairRatingService.updateRepairRating(command.toUpdateCommand(id));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteRepairRating(@NotNull @PathVariable Long id) {
        repairRatingService.deleteRepairRating(id);
    }

    @Data
    static class RestRepairRatingCommand{

        @NotBlank
        private Rating rating;

        private String comment;

        CreateRepairRatingCommand toCreateCommand() {
            return new CreateRepairRatingCommand(rating, comment);
        }

        UpdateRepairRatingCommand toUpdateCommand(Long id) {
            return new UpdateRepairRatingCommand(id, rating, comment);
        }
    }
}
