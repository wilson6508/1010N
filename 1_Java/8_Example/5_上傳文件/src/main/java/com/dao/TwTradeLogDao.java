package com.dao;

import com.pojo.entity.SearchLogEntity;
import com.pojo.entity.TwTradeLogEntity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface TwTradeLogDao {

//    @Insert("INSERT user_info(user_id, user_info, result) VALUES(#{userId}, #{userInfo}, #{result})")
//    int insert(UserInfoEntity entity);
//
    @Select("select * from tw_trade_log where stock_id = #{id}")
    List<TwTradeLogEntity> getByStockId(String stockId);

    @Select("select * from tw_trade_log limit 5, 5;")
    List<TwTradeLogEntity> getAll();

    @Insert("insert tw_trade_log values (null, #{tradeDate}, #{stockId}, #{quantity}, #{payment});")
    void insert(TwTradeLogEntity entity);


//    @Update("UPDATE user_info SET user_id = #{userId}, user_info = #{userInfo}, result = #{result} WHERE id = #{id}")
//    int update(UserInfoEntity entity);
//
//    @Delete("DELETE FROM user_info WHERE id = #{id}")
//    int delete(int id);

}
