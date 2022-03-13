package pl.loka.vehiclemanager.user.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.loka.vehiclemanager.workshop.domain.Workshop;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Dealer extends UserEntity {

    @OneToMany(cascade = CascadeType.ALL)
    @JsonIgnoreProperties("dealer")
    private List<Workshop> workshops;

    public Dealer(String email, String password, String friendName) {
        super(email, password, friendName);
    }
}
