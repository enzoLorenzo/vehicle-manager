package pl.loka.vehiclemanager.user.application;

public class PasswordNoMatchException extends RuntimeException {
    public PasswordNoMatchException(String message) {
        super(message);
    }
}
