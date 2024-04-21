package com;

import com.dao.TwTradeLogDao;
import com.pojo.entity.TwTradeLogEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ApplicationTests {

    @Autowired
    TwTradeLogDao twTradeLogDao;

    @Test
    void contextLoads() {
        TwTradeLogEntity entity = new TwTradeLogEntity();
        entity.setTradeDate("2023-06-21");
        entity.setStockId("AAPL");
        entity.setQuantity(2);
        entity.setPayment(500);
        twTradeLogDao.insert(entity);
        System.out.println("done");
    }

}
