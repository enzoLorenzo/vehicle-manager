package pl.loka.vehiclemanager.security.user_details;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.loka.vehiclemanager.user.db.ClientJpaRepository;
import pl.loka.vehiclemanager.user.db.DealerJpaRepository;
import pl.loka.vehiclemanager.user.domain.Client;
import pl.loka.vehiclemanager.user.domain.Dealer;

import java.util.Optional;
import java.util.stream.Stream;

@AllArgsConstructor
@Service
public class VMUserDetailsService implements UserDetailsService {

    private final ClientJpaRepository clientRepository;
    private final DealerJpaRepository dealerRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Client> client = clientRepository.findByUsernameIgnoreCase(username);
        Optional<Dealer> dealer = dealerRepository.findByUsernameIgnoreCase(username);
        return Stream.of(client, dealer)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .findFirst()
                .map(UserEntityDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException(username));
    }
}
