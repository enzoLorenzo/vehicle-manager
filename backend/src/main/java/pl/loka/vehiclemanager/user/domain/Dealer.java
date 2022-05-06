package pl.loka.vehiclemanager.user.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.loka.vehiclemanager.workshop.domain.Workshop;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "dealer")
public class Dealer extends UserEntity {

    @JsonIgnore
    @OneToMany(mappedBy="dealer")
    private List<Workshop> workshops;

    public Dealer(String username, String password, String nickname) {
        super(username, password, nickname);
    }
}
