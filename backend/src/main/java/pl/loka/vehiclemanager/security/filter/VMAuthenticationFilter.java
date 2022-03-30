package pl.loka.vehiclemanager.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import pl.loka.vehiclemanager.security.token.TokenUtils;
import pl.loka.vehiclemanager.security.user_details.UserEntityDetails;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static pl.loka.vehiclemanager.security.token.TokenUtils.prepareErrorResponse;

@Slf4j
public class VMAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final ObjectMapper mapper = new ObjectMapper();

    @SneakyThrows
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        LoginCommand command = mapper.readValue(request.getReader(), LoginCommand.class);
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(command.getUsername(), command.getPassword());
        return this.getAuthenticationManager().authenticate(token);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException {
        UserEntityDetails user = (UserEntityDetails) authentication.getPrincipal();

        String accessToken = TokenUtils.generateAccessToken(user, request.getRequestURL().toString());
        String refreshToken = TokenUtils.generateRefreshToken(user, request.getRequestURL().toString());

        TokenUtils.prepareAuthenticateResponse(response, accessToken, refreshToken, user.getUserId());
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException ex) throws IOException {
        log.info("Error logging in: {}", ex.getMessage());
        prepareErrorResponse(response, ex);
    }

    @Data
    static class LoginCommand {
        private String username;
        private String password;
    }
}
