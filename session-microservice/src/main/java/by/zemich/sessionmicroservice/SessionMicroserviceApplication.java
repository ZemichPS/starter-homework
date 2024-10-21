package by.zemich.sessionmicroservice;

import by.zemich.sessionmicroservice.properties.SessionConfigProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({SessionConfigProperties.class})
public class SessionMicroserviceApplication {

    public static void main(String[] args) {
        SpringApplication.run(SessionMicroserviceApplication.class, args);
    }

}
