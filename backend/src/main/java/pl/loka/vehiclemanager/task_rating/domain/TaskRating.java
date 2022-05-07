package pl.loka.vehiclemanager.task_rating.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.loka.vehiclemanager.common.BaseEntity;
import pl.loka.vehiclemanager.task.domain.Task;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import static pl.loka.vehiclemanager.task_rating.application.port.TaskRatingUseCase.CreateTaskRatingCommand;
import static pl.loka.vehiclemanager.task_rating.application.port.TaskRatingUseCase.UpdateTaskRatingCommand;


@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "task_rating")
public class TaskRating extends BaseEntity {
    private int rating;
    private String comment;

    @OneToOne
    @JoinColumn(name = "task_id", referencedColumnName = "id")
    private Task task;

    public TaskRating(CreateTaskRatingCommand command, Task task) {
        this.rating = command.rating();
        this.comment = command.comment();
        this.task = task;
    }

    public void update(UpdateTaskRatingCommand command) {
        if (command.rating() > 0) {
            this.rating = command.rating();
        }
    }
}
