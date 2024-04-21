package com.example.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class HelloAop {

    @Pointcut(value = "execution(* com.example.service.*.*(..))")
    public void pointcut() {}

    @After("pointcut()")
    public void afterTestMethod() {
        log.info("HelloAop");
    }

}
