package com.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface MyClassTag {
    String name() default "";
    int age() default 0;
}

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface MyClassTag {
    @Value("${myProp.name}")
    String name() default "";
    @Value("${myProp.age}")
    int age() default 0;
}
