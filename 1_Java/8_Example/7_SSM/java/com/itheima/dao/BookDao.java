package com.itheima.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itheima.domain.Book;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BookDao extends BaseMapper<Book> {}

// import org.apache.ibatis.annotations.Select;
//    @Select("select * from book where id = #{id}")
//    Book getById(Integer id);