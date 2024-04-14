package com.itheima.service;

import org.springframework.stereotype.Service;

@Service
public class UserService {

    public String test(String name) {
        return "test" + name;
    }

}
