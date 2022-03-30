package pl.loka.vehiclemanager.task.application;

public class TaskNotFoundException extends RuntimeException{
    public TaskNotFoundException(String message) { super(message); }
}
