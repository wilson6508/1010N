package com.dao;

import com.pojo.entity.UserInfoEntity;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserInfoDao {

    @Insert("INSERT user_info(user_id, user_info, result) VALUES(#{userId}, #{userInfo}, #{result})")
    int insert(UserInfoEntity entity);

    @Select("SELECT * FROM user_info WHERE id = #{id}")
    UserInfoEntity getById(Integer id);

    @Select("SELECT * FROM user_info")
    List<UserInfoEntity> getAll();

    @Update("UPDATE user_info SET user_id = #{userId}, user_info = #{userInfo}, result = #{result} WHERE id = #{id}")
    int update(UserInfoEntity entity);

    @Delete("DELETE FROM user_info WHERE id = #{id}")
    int delete(int id);

}
