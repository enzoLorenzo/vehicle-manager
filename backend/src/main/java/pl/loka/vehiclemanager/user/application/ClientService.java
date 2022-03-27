package pl.loka.vehiclemanager.user.application;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.loka.vehiclemanager.security.application.UserSecurity;
import pl.loka.vehiclemanager.security.user_details.UserEntityDetails;
import pl.loka.vehiclemanager.user.application.port.UserUseCase;
import pl.loka.vehiclemanager.user.db.ClientJpaRepository;
import pl.loka.vehiclemanager.user.domain.Client;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.io.IOException;
import java.util.Collections;

import static pl.loka.vehiclemanager.security.token.TokenUtils.*;

@Slf4j
@Service
@AllArgsConstructor
public class ClientService implements UserUseCase {

    private final ClientJpaRepository repository;
    private final PasswordEncoder encoder;
    private final UserSecurity security;


    @Override
    public RegisterResponse register(RegisterCommand command) {
        if (repository.findByUsernameIgnoreCase(command.username()).isPresent()) {
            return RegisterResponse.failure(Collections.singletonList("Client already exists"));
        }
        Client newClient = new Client(command.username(), encoder.encode(command.password()), command.friendName());
        return RegisterResponse.success(repository.save(newClient));
    }

    @Override
    @Transactional
    public void deregister(Long userId) {
        if (!repository.existsById(userId)) {
            throw new UserNotFoundException("Not found user with id: " + userId);
        }
        repository.deleteById(userId);
    }

    @Override
    public Client getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Not found client with id: " + id));
    }

    @Override
    public Client getByUsername(String username) {
        return repository.findByUsernameIgnoreCase(username)
                .orElseThrow(() -> new UserNotFoundException("Not found client with username: " + username));
    }

    @Override
    public void update(UpdateCommand command) {
        Client client = getById(command.id());
        isOwner(client.getUsername());
        client.update(command);
        repository.save(client);
    }

    @Override
    public void updatePassword(UpdatePasswordCommand command) {
        Client client = getById(command.userId());
        isOwner(client.getUsername());
        client.changePassword(encoder.encode(command.oldPassword()), encoder.encode(command.newPassword()));
        repository.save(client);
    }

    @Override
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            String refreshToken = getTokenFromHeader(request);
            String username = getUsernameFromToken(refreshToken);
            UserEntityDetails user = new UserEntityDetails(getByUsername(username));
            String accessToken = generateAccessToken(user, request.getRequestURL().toString());
            String newRefreshToken = generateRefreshToken(user, request.getRequestURL().toString());
            prepareAuthenticateResponse(response, accessToken, newRefreshToken, user.getUserId());
        } catch (Exception ex) {
            log.info("Error logging in: {}", ex.getMessage());
            prepareErrorResponse(response, ex);
        }
    }

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        security.logout(request, response);
    }

    private void isOwner(String ownerName) {
        if (!security.isOwner(ownerName)) {
            throw new AccessDeniedException("Unauthorized user");
        }
    }
}
