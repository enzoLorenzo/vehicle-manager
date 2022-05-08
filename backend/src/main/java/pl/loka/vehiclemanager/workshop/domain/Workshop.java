package pl.loka.vehiclemanager.workshop.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.loka.vehiclemanager.common.BaseEntity;
import pl.loka.vehiclemanager.pricelist.domain.PriceListPosition;
import pl.loka.vehiclemanager.task.domain.Task;
import pl.loka.vehiclemanager.user.domain.Dealer;
import pl.loka.vehiclemanager.workshop.application.port.WorkshopUseCase.CreateWorkshopCommand;
import pl.loka.vehiclemanager.workshop.application.port.WorkshopUseCase.UpdateWorkshopCommand;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "workshop")
public class Workshop extends BaseEntity {

    private String name;
    private String address;
    private String description;

    @OneToMany(mappedBy = "workshop")
    private List<PriceListPosition> priceList;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "dealer_id", nullable = false)
    private Dealer dealer;

    @OneToMany(
            mappedBy = "workshop",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<Task> tasks;

    public Workshop(CreateWorkshopCommand command, Dealer dealer) {
        this.name = command.name();
        this.address = command.address();
        this.description = command.description();
        this.dealer = dealer;
        this.priceList = new ArrayList<>();
    }

    public void update(UpdateWorkshopCommand command) {
        if (command.name() != null) {
            this.name = command.name();
        }
        if (command.address() != null) {
            this.address = command.address();
        }
        if (command.description() != null) {
            this.description = command.description();
        }
    }
}
