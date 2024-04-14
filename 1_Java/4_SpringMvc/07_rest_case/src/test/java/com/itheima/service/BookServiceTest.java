//package com.itheima.service;
//
//import com.google.gson.Gson;
//import com.itheima.config.SpringConfig;
//import com.itheima.entity.Book;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//
//import javax.annotation.Resource;
//import java.util.List;
//
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(classes = SpringConfig.class)
//public class BookServiceTest {
//
//    @Resource
//    private BookService bookService;
//
//    @Test
//    public void testGetById() {
//        Book book = bookService.getById(1);
//        System.out.println(new Gson().toJson(book));
//    }
//
//    @Test
//    public void testGetAll() {
//        List<Book> all = bookService.getAll();
//        System.out.println(new Gson().toJson(all));
//    }
//
//}
