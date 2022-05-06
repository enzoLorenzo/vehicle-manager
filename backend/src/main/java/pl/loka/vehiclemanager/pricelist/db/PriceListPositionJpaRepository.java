package pl.loka.vehiclemanager.pricelist.db;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.loka.vehiclemanager.pricelist.domain.PriceListPosition;

import java.util.List;

public interface PriceListPositionJpaRepository extends JpaRepository <PriceListPosition, Long> {
    List<PriceListPosition> findPriceListPositionByWorkshop_Id(Long workshopId);
}
