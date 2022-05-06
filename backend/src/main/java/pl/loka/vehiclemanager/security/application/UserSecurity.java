package pl.loka.vehiclemanager.security.application;

import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Component;
import pl.loka.vehiclemanager.security.user_details.AuthenticationFacade;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@AllArgsConstructor
public class UserSecurity {

    private final AuthenticationFacade authenticationFacade;

    public boolean isOwner(String ownerName) {
        return getLoginUsername()
                .equalsIgnoreCase(ownerName);
    }

    public String getLoginUsername() {
        return authenticationFacade.getAuthentication().getName();
    }

    public void logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = authenticationFacade.getAuthentication();
        new SecurityContextLogoutHandler().logout(request, response, auth);
    }
}
