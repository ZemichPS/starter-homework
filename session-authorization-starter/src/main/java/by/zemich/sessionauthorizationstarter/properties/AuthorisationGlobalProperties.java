package by.zemich.sessionauthorizationstarter.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.HashSet;
import java.util.Set;

@Data
@ConfigurationProperties(prefix = "authorization")
public class AuthorisationGlobalProperties {
    private boolean enabled = false;
    private String sessionServiceHost;
}
