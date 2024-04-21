package com.controller;

import com.model.info.Person;
import com.service.CacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class CacheController {

    @Autowired
    CacheService cacheService;

    @PostMapping("/cache")
    public String cache(@RequestBody Person person) {
        return cacheService.sayHello(person);
    }

}
