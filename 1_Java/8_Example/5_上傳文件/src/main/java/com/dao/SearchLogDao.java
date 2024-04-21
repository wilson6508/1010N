package com.dao;

import com.pojo.entity.SearchLogEntity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface SearchLogDao {

    @Select("SELECT * FROM search_log WHERE id = #{id}")
    SearchLogEntity getById(Integer id);

    @Insert("INSERT search_log(front_name, model_name, query_json, result) VALUES(#{frontName}, #{modelName}, #{queryJson}, #{result})")
    void insert(SearchLogEntity entity);

}
