package pl.loka.vehiclemanager.security.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;

import static pl.loka.vehiclemanager.security.token.TokenUtils.*;

@Slf4j
public class VMAuthorizationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (request.getServletPath().equals("/login") ||
                request.getServletPath().equals("/client/refresh-token") ||
                request.getServletPath().equals("/client/logout") ||
                request.getServletPath().equals("/dealer/refresh-token") ||
                request.getServletPath().equals("/dealer/logout") ||
                request.getServletPath().equals("/client") ||
                request.getServletPath().equals("/dealer")
        ) {
            filterChain.doFilter(request, response);
        } else {
            try {
                String token = getTokenFromHeader(request);
                String username = getUsernameFromToken(token);
                Collection<SimpleGrantedAuthority> roles = getRolesFromToken(token);

                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(username, null, roles);

                SecurityContextHolder.getContext().setAuthentication((authenticationToken));
                filterChain.doFilter(request, response);
            } catch (Exception ex) {
                log.info("Error logging in: {}", ex.getMessage());
                prepareErrorResponse(response, ex);
            }
        }
    }
}
