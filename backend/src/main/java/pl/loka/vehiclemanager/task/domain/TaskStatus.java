package pl.loka.vehiclemanager.task.domain;

import java.util.Arrays;
import java.util.Optional;

public enum TaskStatus {
    PENDING {
        @Override
        public UpdateStatusResult updateStatus(TaskStatus status) {
            switch (status) {
                case IN_PROGRESS:
                    return UpdateStatusResult.ok(IN_PROGRESS);
                case CANCELLED:
                    return UpdateStatusResult.revoked(CANCELLED);
                default:
                    return super.updateStatus(status);
            }
        }
    },
    IN_PROGRESS {
        @Override
        public UpdateStatusResult updateStatus(TaskStatus status) {
            switch (status) {
                case FINISHED:
                    return UpdateStatusResult.ok(FINISHED);
                case CANCELLED:
                    return UpdateStatusResult.revoked(CANCELLED);
                case ON_HOLD:
                    return UpdateStatusResult.ok(ON_HOLD);
                default:
                    return super.updateStatus(status);
            }
        }
    },
    ON_HOLD,
    CANCELLED,
    FINISHED;

    public static Optional<TaskStatus> parseString(String value) {
        return Arrays.stream(values())
                .filter(it -> it.name().equals(value))
                .findFirst();
    }

    public UpdateStatusResult updateStatus(TaskStatus status) {
        throw new IllegalArgumentException("Unable to mark " + this.name() + " task as " + status.name());
    }
}
