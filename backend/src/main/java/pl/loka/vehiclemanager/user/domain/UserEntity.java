package pl.loka.vehiclemanager.user.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.loka.vehiclemanager.common.BaseEntity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class UserEntity extends BaseEntity {

    private String friendName;
    private String email;
    private String password;
    private boolean active;
    private boolean blocked;

    @CollectionTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id")
    )
    @Column(name = "role")
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<String> roles = new HashSet<>();

    public UserEntity(String email, String password, String friendName) {
        this.email = email;
        this.password = password;
        this.friendName = friendName;
        this.active = true;
        this.roles = Set.of("ROLE_USER");
    }

    public void changePassword(String oldPassword, String newPassword) {
        if (!Objects.equals(this.password, oldPassword)) {
            throw new PasswordNoMatchException("The password provided is incorrect");
        }
        this.password = newPassword;
    }
}
