package pl.loka.vehiclemanager.security.user_details;

import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import pl.loka.vehiclemanager.user.domain.UserEntity;

import java.util.Collection;
import java.util.Collections;

@AllArgsConstructor
public class UserEntityDetails implements UserDetails {
    private final UserEntity entity;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        String role = entity.getRole();
        return Collections.singleton(new SimpleGrantedAuthority(role));
    }

    @Override
    public String getPassword() {
        return entity.getPassword();
    }

    @Override
    public String getUsername() {
        return entity.getUsername();
    }

    public Long getUserId() {
        return entity.getId();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !entity.isBlocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return entity.isActive();
    }

    public UserEntity getEntity() {
        return entity;
    }
}

