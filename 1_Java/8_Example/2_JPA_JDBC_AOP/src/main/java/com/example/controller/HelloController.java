package com.example.controller;

import com.example.service.HelloService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class HelloController {

    @Resource
    private HelloService helloService;

    @GetMapping("/hello")
    public String hello() {
        return helloService.hello();
    }

}
