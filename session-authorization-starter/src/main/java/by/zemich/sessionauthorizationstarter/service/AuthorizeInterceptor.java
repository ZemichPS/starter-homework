package by.zemich.sessionauthorizationstarter.service;

import by.zemich.sessionauthorizationstarter.annotation.Authorize;
import by.zemich.sessionauthorizationstarter.dto.Session;
import by.zemich.sessionauthorizationstarter.dto.SessionRequest;
import by.zemich.sessionauthorizationstarter.exception.UserBannedException;
import by.zemich.sessionauthorizationstarter.properties.AuthorisationGlobalProperties;
import by.zemich.sessionauthorizationstarter.service.api.BlackListDataSource;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Method;
import java.util.Arrays;

@AllArgsConstructor
@Slf4j
public class AuthorizeInterceptor implements MethodInterceptor {

    private final Object originalBean;
    private final BeanFactory beanFactory;
    private final RestClientService restClientService;

    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {

        if (method.isAnnotationPresent(Authorize.class)) {

            args = Arrays.stream(args).map(
                    arg -> {
                        if (arg instanceof SessionRequest sessionRequest) {
                            String login = sessionRequest.getLogin();
                            authorize(login, getDataSource(method));
                            Session newSession = restClientService.requestSessionByLogin(login);
                            sessionRequest.setSession(newSession);
                            return sessionRequest;
                        }
                        return arg;
                    }
            ).toArray();
        }
        return method.invoke(originalBean, args);
    }

    private BlackListDataSource getDataSource(Method method) {
        Authorize authorize = method.getAnnotation(Authorize.class);
        Class<? extends BlackListDataSource> beanClazz = authorize.blackListDataSource();
        return beanFactory.getBean(beanClazz);
    }

    void authorize(String userLogin, BlackListDataSource dataSource) {
        boolean banned = Arrays.asList(dataSource.getBlackList()).contains(userLogin);
        if (banned) {
            log.info("User with login: {} is was banned", userLogin);
            throw new UserBannedException("User %s is banned for this service".formatted(userLogin));
        }
    }

}
