package pl.loka.vehiclemanager.vehicle.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.loka.vehiclemanager.common.BaseEntity;
import pl.loka.vehiclemanager.task.domain.Task;
import pl.loka.vehiclemanager.user.domain.Client;

import javax.persistence.*;

import java.util.List;

import static pl.loka.vehiclemanager.vehicle.application.port.VehicleUseCase.CreateVehicleCommand;
import static pl.loka.vehiclemanager.vehicle.application.port.VehicleUseCase.UpdateVehicleCommand;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "vehicle")
public class Vehicle extends BaseEntity {

    private String registration;
    private String brand;
    private String model;
    private String generation;
    private String year;
    private String engineCapacity;
    private String horsePower;

    @Enumerated(EnumType.STRING)
    private VehicleType type;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name="owner_id", nullable=false)
    private Client owner;

    @OneToMany(mappedBy = "vehicle",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<Task> tasks;


    public Vehicle(CreateVehicleCommand command, Client owner) {
        this.registration = command.registration();
        this.brand = command.brand();
        this.model = command.model();
        this.generation = command.generation();
        this.year = command.year();
        this.engineCapacity = command.engineCapacity();
        this.horsePower = command.horsePower();
        this.type = command.type();
        this.owner = owner;
    }

    public void update(UpdateVehicleCommand command) {
        if(command.registration() != null){
            this.registration = command.registration();
        }
        if(command.brand() != null){
            this.brand = command.brand();
        }
        if(command.model() != null){
            this.model = command.model();
        }
        if(command.generation() != null){
            this.generation = command.generation();
        }
        if(command.year() != null){
            this.year = command.year();
        }
        if(command.engineCapacity() != null){
            this.engineCapacity = command.engineCapacity();
        }
        if(command.horsePower() != null){
            this.horsePower = command.horsePower();
        }
        if(command.type() != null){
            this.type = command.type();
        }
    }
}
