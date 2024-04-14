package com.itheima.controller;

import com.itheima.pojo.Person;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/books")
public class BookController {

    // https://sites.google.com/im.fju.edu.tw/web/spring-framework-web/spring-controller

    @GetMapping
    public Person person() {
        return new Person("QQQ", 80);
    }

}
