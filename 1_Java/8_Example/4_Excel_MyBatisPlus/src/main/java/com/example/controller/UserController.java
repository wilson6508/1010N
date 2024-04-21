package com.example.controller;

import com.example.entity.User;
import com.example.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private IUserService userService;

    @GetMapping
    public List<User> getList() {
        return userService.list();
    }

    @GetMapping("/createNormalExcel")
    public String createNormalExcel() {
        userService.createNormalExcel();
        return "ok";
    }

}
