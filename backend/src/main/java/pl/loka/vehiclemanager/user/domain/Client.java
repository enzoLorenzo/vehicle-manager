package pl.loka.vehiclemanager.user.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.loka.vehiclemanager.vehicle.domain.Vehicle;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "clients")
public class Client extends UserEntity {

    @OneToMany(mappedBy="owner")
    private List<Vehicle> vehicles;

    public Client(String username, String password, String nickname) {
        super(username, password, nickname);
    }
}
