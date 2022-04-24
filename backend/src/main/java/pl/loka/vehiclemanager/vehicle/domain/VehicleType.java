package pl.loka.vehiclemanager.vehicle.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum VehicleType {
    CAR("OSOBOWE"),
    VAN("DOSTAWCZE"),
    MOTORCYCLE("MOTOCYKL"),
    TRUCK("CIĘŻAROWY"),
    CONSTRUCTION("BUDOWLANE"),
    FARM("ROLNICZY");

    private final String value;
}
