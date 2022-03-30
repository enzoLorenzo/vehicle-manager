package pl.loka.vehiclemanager.security;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import pl.loka.vehiclemanager.security.filter.VMAuthenticationFilter;
import pl.loka.vehiclemanager.security.filter.VMAuthorizationFilter;
import pl.loka.vehiclemanager.security.user_details.VMUserDetailsService;
import pl.loka.vehiclemanager.user.db.ClientJpaRepository;
import pl.loka.vehiclemanager.user.db.DealerJpaRepository;

import static org.springframework.http.HttpMethod.POST;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
class VMSecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final ClientJpaRepository clientRepository;
    private final DealerJpaRepository dealerRepository;
    private final String allowedOrigins;

    VMSecurityConfiguration(
            @Value("${app.security.allowedOrigins}") String allowedOrigins,
            ClientJpaRepository clientRepository,
            DealerJpaRepository dealerRepository) {
        this.clientRepository = clientRepository;
        this.dealerRepository = dealerRepository;
        this.allowedOrigins = allowedOrigins;
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(authenticationProvider());
    }

    @Bean
    AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        VMUserDetailsService detailsService = new VMUserDetailsService(clientRepository, dealerRepository);
        provider.setUserDetailsService(detailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.cors();
        http
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .mvcMatchers(POST, "/login/**", "/client", "/client/refresh-token/**").permitAll()
//                .mvcMatchers("/swagger-ui/**", "/api-docs/**").hasRole("ADMIN")
                .anyRequest().authenticated()
                .and()
                .addFilterBefore(authorizationFilter(), UsernamePasswordAuthenticationFilter.class)
                .addFilter(authenticationFilter())
                .logout();
    }

    @SneakyThrows
    private VMAuthorizationFilter authorizationFilter() {
        return new VMAuthorizationFilter();
    }

    @SneakyThrows
    private VMAuthenticationFilter authenticationFilter() {
        VMAuthenticationFilter filter = new VMAuthenticationFilter();
        filter.setAuthenticationManager(super.authenticationManager());
        return filter;
    }
}
