package pl.loka.vehiclemanager.vehicle.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.loka.vehiclemanager.common.BaseEntity;
import pl.loka.vehiclemanager.user.domain.Client;

import javax.persistence.*;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "vehicles")
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
    @JsonIgnoreProperties("vehicles")
    private Client owner;


}
