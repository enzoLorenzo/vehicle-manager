package pl.loka.vehiclemanager.task.domain;

public record UpdateStatusResult(TaskStatus newStatus, boolean revoked) {
    static UpdateStatusResult ok(TaskStatus newStatus) {
        return new UpdateStatusResult(newStatus, false);
    }

    static UpdateStatusResult revoked(TaskStatus newStatus) {
        return new UpdateStatusResult(newStatus, true);
    }
}
