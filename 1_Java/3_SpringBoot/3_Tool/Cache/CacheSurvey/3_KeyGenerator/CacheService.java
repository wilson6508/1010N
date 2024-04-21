package com.service;

import com.model.info.Person;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.lang.reflect.Method;
import java.util.Random;

@Service
public class CacheService {

//    @Bean("customKeyGenerator")
//    public KeyGenerator keyGenerator() {
//        return (target, method, params) -> {
//            System.out.println(target.getClass().getSimpleName());
//            System.out.println(method.getName());
//            System.out.println(((Person) params[0]).getName());
//            return new Random().nextInt(100);
//        };
//    }

    @Bean("customKeyGenerator")
    public KeyGenerator keyGenerator() {
        return (target, method, params) -> ((Person) params[0]).getName();
    }

    @Cacheable(value = "user", keyGenerator = "customKeyGenerator")
    public String sayHello(Person person) {
        System.out.println("進入方法");
        return "test";
    }

}
