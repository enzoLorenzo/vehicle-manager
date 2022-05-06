package pl.loka.vehiclemanager.configuration;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.loka.vehiclemanager.task.application.port.TaskUseCase;
import pl.loka.vehiclemanager.task.domain.TaskStatus;
import pl.loka.vehiclemanager.user.application.port.UserUseCase;
import pl.loka.vehiclemanager.user.domain.Client;
import pl.loka.vehiclemanager.user.domain.Dealer;
import pl.loka.vehiclemanager.vehicle.db.VehicleJpaRepository;
import pl.loka.vehiclemanager.vehicle.domain.Vehicle;
import pl.loka.vehiclemanager.vehicle.domain.VehicleType;
import pl.loka.vehiclemanager.workshop.db.WorkshopJpaRepository;
import pl.loka.vehiclemanager.workshop.domain.Workshop;

import java.time.LocalDateTime;
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
    private final TaskUseCase taskService;



    public LoadDatabase(
            @Qualifier("clientService") UserUseCase clientService,
            @Qualifier("dealerService") UserUseCase dealerService,
            VehicleJpaRepository vehicleJpaRepository,
            WorkshopJpaRepository workshopJpaRepository,
            TaskUseCase taskService) {
        this.clientService = clientService;
        this.dealerService = dealerService;
        this.vehicleJpaRepository = vehicleJpaRepository;
        this.workshopJpaRepository = workshopJpaRepository;
        this.taskService = taskService;
    }

    @Bean
    void InitDatabase() {
        clientService.register(new UserUseCase.RegisterCommand("user", "user", "Marcin Kapustas"));
        Client client = (Client) clientService.getByUsername("user");

        Vehicle vehicle1 = addVehicle(client, "KR10101", "Audi", "S5", "", "2015", "2.0", "210", VehicleType.CAR);
        Vehicle vehicle2 = addVehicle(client, "KR20202", "BMW", "M4", "Competition", "2019", "3.0", "510", VehicleType.CAR);


        dealerService.register(new UserUseCase.RegisterCommand("dealer", "dealer", "Szymon Lorenzo"));
        Dealer dealer = (Dealer) dealerService.getByUsername("dealer");

        Workshop workshop = addWorkshop(dealer, "LorenzoCars", "ul.Długa 34", "Zakład blacharski");

        taskService.addTask(new TaskUseCase.CreateTaskCommand(
                "opis",
                LocalDateTime.of(2022, 4, 1, 12,40),
                LocalDateTime.of(2022, 4, 1, 12,40),
                TaskStatus.PENDING,
                vehicle1.getId(),
                workshop.getId()));

        taskService.addTask(new TaskUseCase.CreateTaskCommand(
                "druga naprawa",
                LocalDateTime.of(2021, 4, 1, 12,20),
                LocalDateTime.of(2022, 5, 1, 10,40),
                TaskStatus.IN_PROGRESS,
                vehicle2.getId(),
                workshop.getId()));
    }

    private Vehicle addVehicle(Client client, String registration, String brand, String model, String generation, String year, String capacity, String hp, VehicleType type) {
        CreateVehicleCommand createVehicleCommand = new CreateVehicleCommand(registration, brand, model, generation, year, capacity, hp, type);
        Vehicle vehicle = new Vehicle(createVehicleCommand, client);
        vehicleJpaRepository.save(vehicle);
        return vehicle;
    }

    private Workshop addWorkshop(Dealer dealer, String name, String address, String description) {
        CreateWorkshopCommand createWorkshopCommand = new CreateWorkshopCommand(name, address, description);
        Workshop workshop = new Workshop(createWorkshopCommand, dealer);
        workshopJpaRepository.save(workshop);
        return workshop;
    }


}
