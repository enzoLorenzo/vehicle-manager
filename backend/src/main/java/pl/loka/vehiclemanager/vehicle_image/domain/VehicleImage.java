package pl.loka.vehiclemanager.vehicle_image.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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
@Table(name = "vehicle_image")
public class VehicleImage extends BaseEntity {

    private Long vehicleId;

    @Lob
    @Column(name = "image", length = Integer.MAX_VALUE)
    private byte[] image;

    public VehicleImage(CreateVehicleImageCommand command) {
        this.vehicleId = command.vehicleId();
        this.image = command.image();
    }
}
