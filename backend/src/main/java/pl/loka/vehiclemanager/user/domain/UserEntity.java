package pl.loka.vehiclemanager.user.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.loka.vehiclemanager.common.BaseEntity;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.util.Objects;

import static pl.loka.vehiclemanager.user.application.port.UserUseCase.UpdateCommand;

@Getter
@Setter
@MappedSuperclass
@NoArgsConstructor
public abstract class UserEntity extends BaseEntity {

    @Column(name = "username")
    private String username;

    private String password;
    private String nickname;
    private boolean active;
    private boolean blocked;
    private String role = "ROLE_USER";

    public UserEntity(String username, String password, String nickname) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.active = true;
    }

    public void changePassword(String oldPassword, String newPassword) {
        if (!Objects.equals(this.password, oldPassword)) {
            throw new PasswordNoMatchException("The password provided is incorrect");
        }
        this.password = newPassword;
    }

    public void update(UpdateCommand command) {
        if (command.nickname() != null) {
            this.nickname = command.nickname();
        }
    }
}
