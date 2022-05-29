package pl.loka.vehiclemanager.pricelist.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.loka.vehiclemanager.common.BaseEntity;
import pl.loka.vehiclemanager.workshop.application.port.WorkshopUseCase;
import pl.loka.vehiclemanager.workshop.domain.Workshop;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import static pl.loka.vehiclemanager.workshop.application.port.WorkshopUseCase.*;

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

//    @ManyToOne
//    @JsonIgnore
//    @JoinColumn(name = "workshop_id", nullable = false)
//    private Workshop workshop;

    public PriceListPosition(PriceListPositionDTO dto, Workshop workshop){
        this.name = dto.name();
        this.description = dto.description();
        this.price = dto.price();
    }
}
