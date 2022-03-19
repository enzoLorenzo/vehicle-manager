package pl.loka.vehiclemanager.common;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

public class Utils {

    public static URI createUri(BaseEntity entity){
        return ServletUriComponentsBuilder.fromCurrentRequestUri().path("/" + entity.getId().toString()).build().toUri();
    }
}
