package by.zemich.sessionauthorizationstarter.listener;

import by.zemich.sessionauthorizationstarter.properties.AuthorisationBlackListProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

@Slf4j
@RequiredArgsConstructor
public class ApplicationStarterListener implements ApplicationListener<ContextRefreshedEvent> {

    private final AuthorisationBlackListProperties properties;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
          log.info("Authorization enabled");
          log.info("default blacklist contains: {}", properties.getBlackList());

    }
}
