package pl.loka.vehiclemanager.pricelist.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.loka.vehiclemanager.common.BaseEntity;
import pl.loka.vehiclemanager.pricelist.application.port.PriceListPositionUseCase.UpdatePriceListPositionCommand;
import pl.loka.vehiclemanager.pricelist.application.port.PriceListPositionUseCase.CreatePriceListPositionCommand;
import pl.loka.vehiclemanager.workshop.domain.Workshop;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "price_list")
public class PriceListPosition extends BaseEntity {
    private String name;
    private String description;
    private Double price;

    @ManyToOne
    @JsonIgnoreProperties("price_list")
    private Workshop workshop;

    public PriceListPosition(CreatePriceListPositionCommand command, Workshop workshop){
        this.name = command.name();
        this.description = command.description();
        this.price = command.price();
        this.workshop = workshop;
    }

    public void update(UpdatePriceListPositionCommand command){
        if(command.name() != null){
            this.name = command.name();
        }
        if(command.description() != null){
            this.description = command.description();
        }
        if(command.price() != null){
            this.price = command.price();
        }
    }
}
