package pl.loka.vehiclemanager.workshop.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.loka.vehiclemanager.common.BaseEntity;
import pl.loka.vehiclemanager.task.domain.Task;
import pl.loka.vehiclemanager.user.domain.Dealer;
import pl.loka.vehiclemanager.workshop.application.port.WorkshopUseCase.UpdateWorkshopCommand;
import pl.loka.vehiclemanager.workshop.application.port.WorkshopUseCase.CreateWorkshopCommand;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "workshop")
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
    @JsonIgnore
    @JoinColumn(name="dealer_id", nullable=false)
    private Dealer dealer;

    @OneToMany(mappedBy = "workshop")
    private List<Task> tasks;

    public Workshop(CreateWorkshopCommand command, Dealer dealer){
        this.friendName = command.friendName();
        this.address = command.address();
        this.description = command.description();
        this.providedServices = command.providedServices();
        this.dealer = dealer;
    }

    public void update(UpdateWorkshopCommand command) {
        if(command.friendName() != null){
            this.friendName = command.friendName();
        }
        if(command.address() != null){
            this.address = command.address();
        }
        if(command.description() != null){
            this.description = command.description();
        }
        if(command.providedServices() != null){
            this.providedServices = command.providedServices();
        }
    }
}
