package by.zemich.sessionauthorizationstarter.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.HashSet;
import java.util.Set;

@Data
@ConfigurationProperties(prefix = "authorization.blacklist")
public class AuthorisationBlackListProperties {
    private final Set<String> blackList = new HashSet<>();
}
