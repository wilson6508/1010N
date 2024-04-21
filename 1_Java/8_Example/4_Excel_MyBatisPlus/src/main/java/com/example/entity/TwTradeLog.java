package com.example.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TwTradeLog {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String tradeDate;
    private String stockId;
    private Integer quantity;
    private Integer payment;
}
