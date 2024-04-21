package com.controller;

import com.entity.TwTradeLogEntity;
import com.google.gson.Gson;
import com.mapper.TwTradeLogDao;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/demo")
public class DemoController {

    @Resource
    private TwTradeLogDao twTradeLogDao;

    @GetMapping("/test")
    public String test() {
        List<TwTradeLogEntity> list = twTradeLogDao.list();
        System.out.println(new Gson().toJson(list));
        return "test";
    }

}
