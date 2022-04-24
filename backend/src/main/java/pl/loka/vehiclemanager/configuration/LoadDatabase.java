package pl.loka.vehiclemanager.configuration;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.loka.vehiclemanager.user.application.port.UserUseCase;
import pl.loka.vehiclemanager.user.domain.Client;
import pl.loka.vehiclemanager.user.domain.Dealer;
import pl.loka.vehiclemanager.vehicle.db.VehicleJpaRepository;
import pl.loka.vehiclemanager.vehicle.domain.Vehicle;
import pl.loka.vehiclemanager.vehicle.domain.VehicleType;
import pl.loka.vehiclemanager.workshop.application.port.WorkshopUseCase;
import pl.loka.vehiclemanager.workshop.db.WorkshopJpaRepository;
import pl.loka.vehiclemanager.workshop.domain.ProvidedService;
import pl.loka.vehiclemanager.workshop.domain.Workshop;

import java.util.Collections;
import java.util.List;

import static pl.loka.vehiclemanager.vehicle.application.port.VehicleUseCase.*;
import static pl.loka.vehiclemanager.workshop.application.port.WorkshopUseCase.*;

@Configuration
public class LoadDatabase {

    private final UserUseCase clientService;
    private final UserUseCase dealerService;
    private final VehicleJpaRepository vehicleJpaRepository;
    private final WorkshopJpaRepository workshopJpaRepository;

    public LoadDatabase(
            @Qualifier("clientService") UserUseCase clientService,
            @Qualifier("dealerService") UserUseCase dealerService,
            VehicleJpaRepository vehicleJpaRepository,
            WorkshopJpaRepository workshopJpaRepository
    ) {
        this.clientService = clientService;
        this.dealerService = dealerService;
        this.vehicleJpaRepository = vehicleJpaRepository;
        this.workshopJpaRepository = workshopJpaRepository;
    }

    @Bean
    void InitDatabase() {
        clientService.register(new UserUseCase.RegisterCommand("user", "user", "Marcin Kapustas"));
        Client client = (Client) clientService.getByUsername("user");

        addVehicle(client, "KR10101", "Audi", "S5", "", "2015", "2.0", "210", VehicleType.CAR);
        addVehicle(client, "KR20202", "BMW", "M4", "Competition", "2019", "3.0", "510", VehicleType.CAR);


        dealerService.register(new UserUseCase.RegisterCommand("dealer", "dealer", "Szymon Lorenzo"));
        Dealer dealer = (Dealer) dealerService.getByUsername("dealer");

        addWorkshop(dealer, "LorenzoCars", "ul.Długa 34", "Zakład blacharski", Collections.emptyList());

    }

    private void addVehicle(Client client, String registration, String brand, String model, String generation, String year, String capacity, String hp, VehicleType type) {
        CreateVehicleCommand createVehicleCommand = new CreateVehicleCommand(registration, brand, model, generation, year, capacity, hp, type);
        Vehicle vehicle = new Vehicle(createVehicleCommand, client);
        vehicleJpaRepository.save(vehicle);
    }

    private void addWorkshop(Dealer dealer, String friendName, String address, String description, List<ProvidedService> providedServices) {
        CreateWorkshopCommand createWorkshopCommand = new CreateWorkshopCommand(friendName, address, description, providedServices);
        Workshop workshop = new Workshop(createWorkshopCommand, dealer);
        workshopJpaRepository.save(workshop);
    }


}
