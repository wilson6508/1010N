package com.itheima.service.impl;

import com.itheima.dao.BookDao;
import com.itheima.service.BookService;

public class BookServiceImpl implements BookService {

    private BookDao bookDao;

    public void save() {
        System.out.println("book service save ...");
        bookDao.save();
    }

    // 提供set方法
    public void setBookDao(BookDao bookDao) {
        this.bookDao = bookDao;
    }
}
