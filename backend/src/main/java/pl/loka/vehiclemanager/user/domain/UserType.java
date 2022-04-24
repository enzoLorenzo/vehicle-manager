package pl.loka.vehiclemanager.user.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserType {

    CLIENT("CLIENT"),
    DEALER("DEALER");

    private final String value;
}
