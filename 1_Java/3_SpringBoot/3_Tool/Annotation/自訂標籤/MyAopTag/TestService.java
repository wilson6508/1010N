package com.service;

import com.annotation.MyAopTag;
import org.springframework.stereotype.Service;

@Service
public class TestService {

    @MyAopTag(name = "Tom", age = 18)
    public void test() {
        System.out.println("enter method");
    }

}
