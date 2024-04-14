package com.itheima.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloController {

    @GetMapping("/hello")
    public String test() {
        return "pages/hello.html";
    }

    @GetMapping("/reHello")
    public String redirect() {
        return "redirect:/pages/hello.html";
    }

}
