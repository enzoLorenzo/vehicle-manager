package pl.loka.vehiclemanager.vehicle_image.domain;

import lombok.*;
import pl.loka.vehiclemanager.common.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;

import static pl.loka.vehiclemanager.vehicle_image.application.port.VehicleImageUseCase.CreateVehicleImageCommand;


@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "vehicle_image")
public class VehicleImage extends BaseEntity {

    private Long vehicleId;

    private String name;

    private String type;

    @Lob
    @Column(name = "image", length = Integer.MAX_VALUE)
    private byte[] image;

    public VehicleImage(CreateVehicleImageCommand command) {
        this.vehicleId = command.vehicleId();
        this.image = command.image();
    }
}
