package by.zemich.sessionauthorizationstarter.configuration;

import by.zemich.sessionauthorizationstarter.bpp.AuthorizeHandlerBeanPostProcessor;
import by.zemich.sessionauthorizationstarter.listener.ApplicationStarterListener;
import by.zemich.sessionauthorizationstarter.properties.AuthorisationBlackListProperties;
import by.zemich.sessionauthorizationstarter.properties.AuthorisationGlobalProperties;
import by.zemich.sessionauthorizationstarter.service.DefaultBlackListDataSource;
import by.zemich.sessionauthorizationstarter.service.RestClientService;
import by.zemich.sessionauthorizationstarter.service.api.BlackListDataSource;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Role;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.client.RestTemplate;

@AutoConfiguration
@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
@EnableAsync
@EnableConfigurationProperties({
        AuthorisationBlackListProperties.class,
        AuthorisationGlobalProperties.class
})
@ConditionalOnProperty(value = "authorization.enabled", havingValue = "true")
public class AuthorizationHandlerConfiguration {

    @Bean
    RestClientService restClientService(AuthorisationGlobalProperties properties, RestTemplate restTemplate){
        return new RestClientService(restTemplate, properties);
    }

    @Bean
    RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder){
        return restTemplateBuilder.build();
    };

    @Bean
    AuthorizeHandlerBeanPostProcessor authorizeHandlerBeanPostProcessor(RestClientService restClientService) {
        return new AuthorizeHandlerBeanPostProcessor(restClientService);
    }

    @Bean
    ApplicationStarterListener applicationStarterListener(AuthorisationBlackListProperties authorisationProperties) {
        return new ApplicationStarterListener(authorisationProperties);
    }

    @Bean
    BlackListDataSource emptyBlackListDataSource(AuthorisationBlackListProperties blackListProperties){
        return new DefaultBlackListDataSource(blackListProperties);
    }
}
