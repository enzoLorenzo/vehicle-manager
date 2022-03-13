package pl.loka.vehiclemanager.user.domain;

public class PasswordNoMatchException extends RuntimeException{
    public PasswordNoMatchException(String message) {
        super(message);
    }
}
