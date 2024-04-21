//package com.hmdp;
//
//import com.hmdp.entity.Shop;
//import com.hmdp.mapper.ShopMapper;
//import com.hmdp.service.impl.ShopServiceImpl;
//import org.junit.jupiter.api.Test;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import javax.annotation.Resource;
//
//@SpringBootTest
//class HmDianPingApplicationTests {
//
//    @Resource
//    ShopMapper shopMapper;
//
//    @Test
//    void test() {
//        Shop shop = new Shop();
//        shop.setId((long) 1);
//        shop.setTypeId((long) 1);
//        shop.setName("1256茶餐厅");
//        shop.setArea("大关");
//        shop.setOpenHours("10:00-22:00");
//        shop.setScore(37);
//        shop.setAvgPrice((long) 80);
//        shop.setComments(3035);
//        shop.setSold(4215);
//        shop.setAddress("金华路锦昌文华苑29号");
//        shopMapper.updateEmp(shop);
//        System.out.println("ok");
//    }
//
//}
