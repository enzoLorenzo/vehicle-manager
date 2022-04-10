package pl.loka.vehiclemanager.configuration;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.loka.vehiclemanager.user.application.port.UserUseCase;

@Configuration
public class LoadDatabase {

    private final UserUseCase clientService;

    public LoadDatabase(@Qualifier("clientService") UserUseCase clientService) {
        this.clientService = clientService;
    }

    @Bean
    void InitDatabase(){
        clientService.register(new UserUseCase.RegisterCommand("user", "user", "Marcin Kapustas"));
    }


}
