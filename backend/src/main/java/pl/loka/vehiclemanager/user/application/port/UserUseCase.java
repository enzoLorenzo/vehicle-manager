package pl.loka.vehiclemanager.user.application.port;

import pl.loka.vehiclemanager.common.Either;
import pl.loka.vehiclemanager.common.ResponseStatus;
import pl.loka.vehiclemanager.user.domain.UserEntity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

public interface UserUseCase {

    RegisterResponse register(RegisterCommand command);

    void deregister(Long userId);

    UserEntity getById(Long id);

    UserEntity getByUsername(String username);

    void update(UpdateCommand toCommand);

    void updatePassword(UpdatePasswordCommand command);

    void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException;

    void logout(HttpServletRequest request, HttpServletResponse response);


    class RegisterResponse extends Either<UserEntity> {

        public RegisterResponse(ResponseStatus status, UserEntity response, List<String> errors) {
            super(status, response, errors);
        }

        public static RegisterResponse success(UserEntity user) {
            return new RegisterResponse(ResponseStatus.OK, user, Collections.emptyList());
        }

        public static RegisterResponse failure(List<String> errors) {
            return new RegisterResponse(ResponseStatus.FAILED, null, errors);
        }
    }

    record RegisterCommand(String username, String password, String friendName) {
    }

    record UpdateCommand(Long id, String friendName) {
    }

    record UpdatePasswordCommand(String oldPassword, String newPassword) {
    }
}
