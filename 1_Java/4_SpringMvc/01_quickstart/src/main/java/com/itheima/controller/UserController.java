package com.itheima.controller;

import com.itheima.pojo.User;
import com.itheima.service.UserService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@RestController
public class UserController {

    @Resource
    private UserService userService;

    // http://127.0.0.1:8080/save?name=Tom
    @PostMapping("/save")
    public String save(String name) {
        System.out.println(name);
        return userService.test(name);
    }

    // http://127.0.0.1:8080/commonPara?name=Tom&age=18
    @GetMapping("/commonPara")
    public String commonPara(String name, int age) {
        return name + "_" + age;
    }

    // http://127.0.0.1:8080/diffPara?age=18
    @GetMapping("/diffPara")
    public String diffPara(@RequestParam(value = "name", required = false, defaultValue = "AAA") String userName, int age) {
        return userName + "_" + age;
    }

    // http://127.0.0.1:8080/pojoPara?age=18
    @GetMapping("/pojoPara")
    public String pojoPara(User user) {
        return user.toString();
    }

    // http://127.0.0.1:8080/arrPara?likes=game&likes=music
    @GetMapping("/arrPara")
    public String arrPara(String[] likes) {
        return Arrays.toString(likes);
    }

    // http://127.0.0.1:8080/listPara?likes=game&likes=music
    @GetMapping("/listPara")
    public String listPara(@RequestParam List<String> likes) {
        return likes.toString();
    }

    // http://127.0.0.1:8080/jsonPojo
    @GetMapping("/jsonPojo")
    public String jsonPojo(@RequestBody User user) {
        return user.toString();
    }

    // http://127.0.0.1:8080/jsonList
    @GetMapping("/jsonList")
    public String jsonList(@RequestBody List<String> likes) {
        return likes.toString();
    }

    // http://127.0.0.1:8080/datePara?d1=2024/01/01&d2=2024-02-02&d3=2024/03/03 12:12:12
    @GetMapping("/datePara")
    public String datePara(Date d1, @DateTimeFormat(pattern = "yyyy-MM-dd") Date d2, @DateTimeFormat(pattern = "yyyy/MM/dd HH:mm:ss") Date d3) {
        return d1.toString() + d2.toString() + d3.toString();
    }

}
