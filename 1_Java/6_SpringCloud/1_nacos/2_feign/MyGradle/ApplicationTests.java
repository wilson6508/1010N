package com;

import com.dao.StockNameDao;
import com.entity.StockNameEntity;
import com.feign.clients.HelloClient;
import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

@SpringBootTest
class ApplicationTests {

	@Resource
	private StockNameDao stockNameDao;

	@Test
	void contextLoads() {
		List<StockNameEntity> all = stockNameDao.findAll();
		System.out.println(new Gson().toJson(all));
	}

	@Resource
	private HelloClient helloClient;

	@Test
	void test() {
		String helloMessage = helloClient.getHelloMessage();
		System.out.println(helloMessage);
	}

}
