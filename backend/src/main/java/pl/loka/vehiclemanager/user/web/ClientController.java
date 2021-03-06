package pl.loka.vehiclemanager.user.web;

import lombok.Data;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.loka.vehiclemanager.common.Utils;
import pl.loka.vehiclemanager.user.application.port.UserUseCase;
import pl.loka.vehiclemanager.user.domain.Client;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.IOException;

import static pl.loka.vehiclemanager.user.application.port.UserUseCase.RegisterCommand;
import static pl.loka.vehiclemanager.user.application.port.UserUseCase.UpdateCommand;

@RestController
@RequestMapping("/client")
public class ClientController {

    private final UserUseCase service;

    public ClientController(@Qualifier("clientService") UserUseCase service) {
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
    public Client getClientById(@PathVariable Long id) {
        return (Client) service.getById(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void updateUser(@PathVariable Long id,
                           @Valid @RequestBody ClientController.RestUpdateCommand command) {
        service.update(command.toCommand(id));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deregisterUser(@NotNull @PathVariable Long id) {
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
        String nickname;

        RegisterCommand toRegisterCommand() {
            return new RegisterCommand(username, password, nickname);
        }
    }

    @Data
    static class RestUpdateCommand {
        private String nickname;

        UpdateCommand toCommand(Long id) {
            return new UpdateCommand(id, nickname);
        }
    }
}

