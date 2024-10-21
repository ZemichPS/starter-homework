package by.zemich.sessionauthorizationstarter.bpp;

import by.zemich.sessionauthorizationstarter.annotation.Authorize;
import by.zemich.sessionauthorizationstarter.properties.AuthorisationGlobalProperties;
import by.zemich.sessionauthorizationstarter.service.AuthorizeInterceptor;
import by.zemich.sessionauthorizationstarter.service.RestClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Constructor;
import java.util.*;

@RequiredArgsConstructor
public class AuthorizeHandlerBeanPostProcessor implements BeanPostProcessor, BeanFactoryAware {
    private final Map<String, Class<?>> beanNamesWithAnnotatedMethods = new HashMap<>();
    private BeanFactory beanFactory;
    private final RestClientService restClientService;


    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        Class<?> clazz = bean.getClass();
        boolean annotationPresent = Arrays.stream(clazz.getDeclaredMethods()).anyMatch(method -> method.isAnnotationPresent(Authorize.class));
        if (annotationPresent) {
            beanNamesWithAnnotatedMethods.put(beanName, clazz);
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return Optional.ofNullable(beanNamesWithAnnotatedMethods.get(beanName))
                .map(clazz -> getProxy(bean))
                .orElse(bean);
    }

    private Object getProxy(Object bean) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(bean.getClass());
        enhancer.setCallback(new AuthorizeInterceptor(bean, beanFactory, restClientService));
        return isPresentDefaultConstructor(bean)
                ? enhancer.create()
                : enhancer.create(getNotDefaultConstructorArgTypes(bean), getNotDefaultConstructorArgs(bean));
    }

    private boolean isPresentDefaultConstructor(Object bean) {
        return Arrays.stream(bean.getClass().getConstructors())
                .anyMatch(constructor -> constructor.getParameterCount() == 0);
    }

    private Class<?>[] getNotDefaultConstructorArgTypes(Object object) {
        return Arrays.stream(object.getClass().getConstructors())
                .max(Comparator.comparingInt(Constructor::getParameterCount))
                .map(Constructor::getParameterTypes)
                .orElseThrow(IllegalArgumentException::new);
    }

    private Object[] getNotDefaultConstructorArgs(Object object) {
        Class<?>[] constructorArgTypes = getNotDefaultConstructorArgTypes(object);
        return Arrays.stream(constructorArgTypes)
                .map(beanFactory::getBean)
                .toArray();
    }
}
