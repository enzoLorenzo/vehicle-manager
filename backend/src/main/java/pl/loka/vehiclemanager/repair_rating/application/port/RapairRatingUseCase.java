package pl.loka.vehiclemanager.repair_rating.application.port;

import pl.loka.vehiclemanager.repair_rating.domain.Rating;
import pl.loka.vehiclemanager.repair_rating.domain.RepairRating;

import java.util.List;

public interface RapairRatingUseCase {

    List<RepairRating> findRepairRatings();

    List<RepairRating> findRepairRatingByTask(Long TaskId);

    RepairRating findRepairRatingById(Long id);

    RepairRating addRepairRating(CreateRepairRatingCommand command);

    void updateRepairRating(UpdateRepairRatingCommand command);

    void deleteRepairRating(Long id);

    record CreateRepairRatingCommand(Rating rating, String comment){

    }

    record UpdateRepairRatingCommand(Long id, Rating rating, String comment){

    }
}
