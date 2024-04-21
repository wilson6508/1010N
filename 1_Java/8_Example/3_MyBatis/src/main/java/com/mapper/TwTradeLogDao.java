package com.mapper;

import com.entity.TwTradeLogEntity;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface TwTradeLogDao {

    @Select("select * from tw_trade_log")
    List<TwTradeLogEntity> list();

}
