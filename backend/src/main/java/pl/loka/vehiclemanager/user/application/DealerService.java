package pl.loka.vehiclemanager.user.application;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.loka.vehiclemanager.security.application.UserSecurity;
import pl.loka.vehiclemanager.security.user_details.UserEntityDetails;
import pl.loka.vehiclemanager.user.application.port.UserUseCase;
import pl.loka.vehiclemanager.user.db.DealerJpaRepository;
import pl.loka.vehiclemanager.user.domain.Dealer;
import pl.loka.vehiclemanager.user.domain.UserType;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

import static pl.loka.vehiclemanager.security.token.TokenUtils.*;

@Slf4j
@Service("dealerService")
@AllArgsConstructor
public class DealerService implements UserUseCase {

    private final DealerJpaRepository repository;
    private final PasswordEncoder encoder;
    private final UserSecurity security;

    @Override
    public RegisterResponse register(RegisterCommand command) {
        if (repository.findByUsernameIgnoreCase(command.username()).isPresent()) {
            return RegisterResponse.failure(Collections.singletonList("Dealer already exist"));
        }
        Dealer newDealer = new Dealer(command.username(), encoder.encode(command.password()), command.nickname());
        return RegisterResponse.success(repository.save(newDealer));
    }

    @Override
    public void deregister(Long userId) {
        if (!repository.existsById(userId)) {
            throw new UserNotFoundException("Not found user with id: " + userId);
        }
        repository.deleteById(userId);
    }

    @Override
    public Dealer getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Not found dealer with id: " + id));
    }

    @Override
    public Dealer getByUsername(String username) {
        return repository.findByUsernameIgnoreCase(username)
                .orElseThrow(() -> new UserNotFoundException("Not found dealer with username: " + username));
    }

    @Override
    public void update(UpdateCommand command) {
        Dealer dealer = getById(command.id());
        isOwner(dealer.getUsername());
        dealer.update(command);
        repository.save(dealer);
    }

    @Override
    public void updatePassword(UpdatePasswordCommand command) {
        Dealer dealer = getByUsername(security.getLoginUsername());
        dealer.changePassword(encoder.encode(command.oldPassword()), encoder.encode(command.newPassword()));
        repository.save(dealer);
    }

    @Override
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            String refreshToken = getTokenFromHeader(request);
            String username = getUsernameFromToken(refreshToken);
            UserEntityDetails user = new UserEntityDetails(getByUsername(username));
            String accessToken = generateAccessToken(user, request.getRequestURL().toString(), UserType.DEALER);
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
