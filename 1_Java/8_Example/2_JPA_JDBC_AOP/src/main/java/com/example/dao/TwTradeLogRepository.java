package com.example.dao;

import com.example.entity.TwTradeLogEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TwTradeLogRepository extends JpaRepository<TwTradeLogEntity, Integer> {

    @Query(value = "select * from tw_trade_log;", nativeQuery = true)
    List<TwTradeLogEntity> findTest();

}
