package com.example.controller;

import com.example.dao.AiPeoplePackageTaskRepository;
import com.example.dao.TwTradeLogRepository;
import com.example.entity.AiPeoplePackageTaskEntity;
import com.example.entity.TwTradeLogEntity;
import com.google.gson.Gson;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/demo")
public class DemoController {

    @Resource
    private TwTradeLogRepository twTradeLogRepository;
    @Resource
    private AiPeoplePackageTaskRepository aiPeoplePackageTaskRepository;

    @GetMapping("/test")
    public String demo() {
        Timestamp timestamp = Timestamp.valueOf(LocalDateTime.now().plusMonths(-1));
        System.out.println(timestamp);
        List<AiPeoplePackageTaskEntity> list = aiPeoplePackageTaskRepository.findByServiceIdAndUpdateTimeIsAfter(74, timestamp);
        System.out.println(new Gson().toJson(list));
        return "demo";
    }

    @Resource
    private JdbcTemplate jdbcTemplate;

    // http://127.0.0.1:8848/demo/ok
    @GetMapping("/ok")
    public String ok() {
//        String sql = "select trade_date from tw_trade_log";
//        List<TwTradeLogEntity> query = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(TwTradeLogEntity.class));
//        System.out.println(new Gson().toJson(query));
        List<TwTradeLogEntity> test = twTradeLogRepository.findTest();
        System.out.println(new Gson().toJson(test));
        return "ok";
    }

}
