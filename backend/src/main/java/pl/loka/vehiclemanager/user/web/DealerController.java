package pl.loka.vehiclemanager.user.web;

import lombok.Data;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.loka.vehiclemanager.common.Utils;
import pl.loka.vehiclemanager.user.application.port.UserUseCase;
import pl.loka.vehiclemanager.user.domain.Dealer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.IOException;

@RestController
@RequestMapping("/dealer")
public class DealerController {

    private final UserUseCase service;

    public DealerController(@Qualifier("dealerService") UserUseCase service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<?> register(@Valid @RequestBody RestRegisterCommand command) {
        return service.register(command.toRegisterCommand())
                .handle(
                        entity -> ResponseEntity.created(Utils.createUri(entity)).build(),
                        errors -> ResponseEntity.badRequest().body(errors)
                );
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Dealer getDealerById(@PathVariable Long id) {
        return (Dealer) service.getById(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void updateDealer(
            @PathVariable Long id,
            @Valid @RequestBody RestUpdateCommand command
    ) {
        service.update(command.toCommand(id));
    }

    @PutMapping("/password")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void updatePassword(
            @Valid @RequestBody RestUpdatePasswordCommand command) {
        service.updatePassword(command.toCommand());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deregisterDealer(@NotNull @PathVariable Long id) {
        service.deregister(id);
    }

    @PostMapping("/refresh-token")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        service.refreshToken(request, response);
    }

    @PostMapping("/logout")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        service.logout(request, response);
    }

    @Data
    static class RestRegisterCommand {
        @Email
        String username;
        @Size(min = 3, max = 16)
        String password;
        @NotBlank
        String friendName;

        UserUseCase.RegisterCommand toRegisterCommand() {
            return new UserUseCase.RegisterCommand(username, password, friendName);
        }
    }

    @Data
    static class RestUpdateCommand {
        private String friendName;

        UserUseCase.UpdateCommand toCommand(Long id) {
            return new UserUseCase.UpdateCommand(id, friendName);
        }
    }

    @Data
    static class RestUpdatePasswordCommand {
        private String oldPassword;
        private String newPassword;

        UserUseCase.UpdatePasswordCommand toCommand() {
            return new UserUseCase.UpdatePasswordCommand(oldPassword, newPassword);
        }
    }
}
