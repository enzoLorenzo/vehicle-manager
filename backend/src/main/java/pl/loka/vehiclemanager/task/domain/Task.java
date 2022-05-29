package pl.loka.vehiclemanager.task.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.loka.vehiclemanager.common.BaseEntity;
import pl.loka.vehiclemanager.pricelist.domain.PriceListPosition;
import pl.loka.vehiclemanager.task.application.port.TaskUseCase.CreateTaskCommand;
import pl.loka.vehiclemanager.task.application.port.TaskUseCase.UpdateTaskCommand;
import pl.loka.vehiclemanager.task_rating.domain.TaskRating;
import pl.loka.vehiclemanager.vehicle.domain.Vehicle;
import pl.loka.vehiclemanager.workshop.domain.Workshop;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "task")
public class Task extends BaseEntity {

    private String description;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    @Enumerated(EnumType.STRING)
    private TaskStatus taskStatus;


    @OneToOne(mappedBy = "task", orphanRemoval = true)
    private TaskRating rating;

    @ManyToOne
    @JsonIgnoreProperties("tasks")
    @JoinColumn(name = "vehicle_id", nullable = false)
    private Vehicle vehicle;

    @ManyToOne
    @JsonIgnoreProperties("tasks")
    @JoinColumn(name = "workshop_id", nullable = false)
    private Workshop workshop;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "task_id")
    private List<PriceListPosition> positions;

    public Task(CreateTaskCommand command, Vehicle vehicle, Workshop workshop) {
        this.description = command.description();
        this.startDate = command.startDate();
        this.endDate = command.endDate();
        this.taskStatus = command.taskStatus();
        this.vehicle = vehicle;
        this.workshop = workshop;
        this.positions = command.positions();
    }

    public void update(UpdateTaskCommand command) {
        if (command.description() != null) {
            this.description = command.description();
        }
        if (command.startDate() != null) {
            this.startDate = command.startDate();
        }
        if (command.endDate() != null) {
            this.endDate = command.endDate();
        }
        if (command.taskStatus() != null) {
            this.taskStatus = command.taskStatus();
        }
    }

    public void updateStatus(TaskStatus status) {
        this.taskStatus.updateStatus(status);
    }
}
