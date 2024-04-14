package com.itheima.controller;

import com.itheima.pojo.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class DemoController {

    // http://127.0.0.1:8080/toJsp
    @GetMapping("/toJsp")
    public String toJsp() {
        return "test.jsp";
    }

    // http://127.0.0.1:8080/toTxt
    @GetMapping("/toTxt")
    @ResponseBody
    public String toTxt() {
        return "toTxt";
    }

    // http://127.0.0.1:8080/toUser
    @GetMapping("/toUser")
    @ResponseBody
    public User toUser() {
        User user = new User();
        user.setName("中文");
        user.setAge(55);
        return user;
    }

}
