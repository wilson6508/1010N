package com.entity;

import lombok.Data;

@Data
public class TwTradeLogEntity {
    private Integer id;
    private String tradeDate;
    private String stockId;
    private Integer quantity;
    private Integer payment;
}
