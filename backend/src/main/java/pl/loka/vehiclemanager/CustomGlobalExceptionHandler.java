package pl.loka.vehiclemanager;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import pl.loka.vehiclemanager.vehicle.application.VehicleNotFoundException;

import java.util.*;

@Slf4j
@RestControllerAdvice
public class CustomGlobalExceptionHandler {

    @ExceptionHandler({VehicleNotFoundException.class})
    public ResponseEntity<Object> handle(Exception ex) {
        log.info(ex.getMessage());
        return handleErrors(HttpStatus.NOT_FOUND, Collections.singletonList(ex.getMessage()));
    }

    private ResponseEntity<Object> handleErrors(HttpStatus status, List<String> errors) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", new Date());
        body.put("status", status.value());
        body.put("errors", errors);
        return new ResponseEntity<>(body, status);
    }
}
