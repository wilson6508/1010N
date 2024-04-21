package com.example.service.impl;

import com.example.dao.TwTradeLogRepository;
import com.example.entity.TwTradeLogEntity;
import com.example.service.HelloService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class HelloServiceImpl implements HelloService {

    @Resource
    private TwTradeLogRepository twTradeLogRepository;

    @Override
    public String hello() {
        TwTradeLogEntity entity = new TwTradeLogEntity();
        entity.setTradeDate("2023-06-06");
        entity.setStockId("App");
        entity.setQuantity(1);
        entity.setPayment(1);
        twTradeLogRepository.save(entity);
        return "hello";
    }

}
