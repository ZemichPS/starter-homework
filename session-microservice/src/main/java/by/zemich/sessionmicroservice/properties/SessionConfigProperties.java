package by.zemich.sessionmicroservice.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "session")
@Data
public class SessionConfigProperties {
    private int validInHours;
}
