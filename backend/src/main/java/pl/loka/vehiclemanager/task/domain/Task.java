package pl.loka.vehiclemanager.task.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.loka.vehiclemanager.common.BaseEntity;
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
    private TaskState taksState;

    @ManyToOne
    @JsonIgnoreProperties("tasks")
    private Vehicle vehicle;

    @ManyToOne
    @JsonIgnoreProperties("tasks")
    private Workshop workshop;

}
