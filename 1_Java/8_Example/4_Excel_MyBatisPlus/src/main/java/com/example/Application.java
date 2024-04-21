package com.example;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.entity.Notification;
import com.example.entity.TwTradeLog;
import com.example.service.INotificationService;
import com.example.service.ITwTradeLogService;
import com.example.service.impl.NotificationServiceImpl;
import com.google.gson.Gson;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.List;
import java.util.Map;

@MapperScan("com.example.mapper")
@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		ConfigurableApplicationContext run = SpringApplication.run(Application.class, args);

//		NotificationServiceImpl bean = run.getBean(NotificationServiceImpl.class);
//		bean.test();

//		ITwTradeLogService bean = run.getBean(ITwTradeLogService.class);
//		QueryWrapper<TwTradeLog> queryWrapper = new QueryWrapper<>();
//		queryWrapper.select("stock_id, COUNT(*) 交易次數, SUM(quantity) 交易金額");
//		queryWrapper.groupBy("stock_id");
//		List<Map<String, Object>> maps = bean.listMaps(queryWrapper);
	}

}
