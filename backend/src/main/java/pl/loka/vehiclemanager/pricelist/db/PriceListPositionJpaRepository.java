package pl.loka.vehiclemanager.pricelist.db;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.loka.vehiclemanager.pricelist.domain.PriceListPosition;

public interface PriceListPositionJpaRepository extends JpaRepository <PriceListPosition, Long> {
}
