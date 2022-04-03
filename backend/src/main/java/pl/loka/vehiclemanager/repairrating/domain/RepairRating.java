package pl.loka.vehiclemanager.repairrating.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.loka.vehiclemanager.common.BaseEntity;
import pl.loka.vehiclemanager.repairrating.application.port.RapairRatingUseCase.UpdateRepairRatingCommand;
import pl.loka.vehiclemanager.repairrating.application.port.RapairRatingUseCase.CreateRepairRatingCommand;
import pl.loka.vehiclemanager.task.domain.Task;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "repair_ratings")
public class RepairRating extends BaseEntity {
    private Rating rating;
    private String comment;

    @OneToOne
    @JsonIgnoreProperties("repair_ratings")
    private Task task;

    public RepairRating(CreateRepairRatingCommand command, Task task) {
        this.rating = command.rating();
        this.comment = command.comment();
        this.task = task;
    }

    public void update(UpdateRepairRatingCommand command){
        if(command.rating() != null){
            this.rating = command.rating();
        }
    }
}
