package by.zemich.sessionauthorizationstarter.annotation;

import by.zemich.sessionauthorizationstarter.service.DefaultBlackListDataSource;
import by.zemich.sessionauthorizationstarter.service.api.BlackListDataSource;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Authorize {

    Class<? extends BlackListDataSource> blackListDataSource() default DefaultBlackListDataSource.class;

    boolean usePropertyBlackList() default true;
}
