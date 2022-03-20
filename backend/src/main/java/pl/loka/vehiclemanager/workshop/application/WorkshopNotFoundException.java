package pl.loka.vehiclemanager.workshop.application;

public class WorkshopNotFoundException extends RuntimeException{
    public WorkshopNotFoundException(String message) { super(message); }
}
