package pl.loka.vehiclemanager.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.function.Function;

@Getter
@AllArgsConstructor
public class Either<R> {
    private final ResponseStatus status;
    private final R response;
    private final List<String> errors;

    public <T> T handle(Function<R, T> onSuccess, Function<List<String>, T> onError) {
        if (status == ResponseStatus.OK) {
            return onSuccess.apply(response);
        } else {
            return onError.apply(errors);
        }
    }
}