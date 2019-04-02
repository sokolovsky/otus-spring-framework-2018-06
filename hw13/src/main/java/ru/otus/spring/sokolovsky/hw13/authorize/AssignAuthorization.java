package ru.otus.spring.sokolovsky.hw13.authorize;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface AssignAuthorization {

    boolean owner() default false;

    String[] permissions() default  {};

    String[] roles() default {};
}
