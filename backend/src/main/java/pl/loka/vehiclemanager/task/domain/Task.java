package pl.loka.vehiclemanager.task.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.jdbc.Work;
import pl.loka.vehiclemanager.common.BaseEntity;
import pl.loka.vehiclemanager.task.application.port.TaskUseCase.UpdateTaskCommand;
import pl.loka.vehiclemanager.task.application.port.TaskUseCase.CreateTaskCommand;

import pl.loka.vehiclemanager.vehicle.domain.Vehicle;
import pl.loka.vehiclemanager.workshop.domain.Workshop;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tasks")
public class Task extends BaseEntity {

    private String description;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    @Enumerated(EnumType.STRING)
    private TaskState taskState;

    @ManyToOne
    @JsonIgnoreProperties("tasks")
    private Vehicle vehicle;

    @ManyToOne
    @JsonIgnoreProperties("tasks")
    private Workshop workshop;

    public Task(CreateTaskCommand command, Vehicle vehicle, Workshop workshop){
        this.description = command.description();
        this.startDate = command.startDate();
        this.endDate = command.endDate();
        this.taskState = command.taskState();
        this.vehicle = vehicle;
        this.workshop = workshop;
    }

    public void update(UpdateTaskCommand command){
        if(command.description() != null){
            this.description = command.description();
        }
        if(command.startDate() != null){
            this.startDate = command.startDate();
        }
        if(command.endDate() != null){
            this.endDate = command.endDate();
        }
        if(command.taskState() != null){
            this.taskState = command.taskState();
        }
    }
}
