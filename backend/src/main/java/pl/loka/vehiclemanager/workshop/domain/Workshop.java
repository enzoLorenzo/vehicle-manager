package pl.loka.vehiclemanager.workshop.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import pl.loka.vehiclemanager.common.BaseEntity;
import pl.loka.vehiclemanager.user.domain.Dealer;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "workshops")
public class Workshop extends BaseEntity {

    private String friendName;
    private String address;
    private String description;

    @CollectionTable(
            name = "workshop_provided_services",
            joinColumns = @JoinColumn(name = "workshop_id")
    )
    @ElementCollection(targetClass = ProvidedService.class, fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    @Column(name = "provided_service")
    private List<ProvidedService> providedServices;

    @ManyToOne
    @JsonIgnoreProperties("workshops")
    private Dealer dealer;
}
