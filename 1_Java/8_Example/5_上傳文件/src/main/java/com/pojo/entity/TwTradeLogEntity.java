package com.pojo.entity;

import lombok.Data;

@Data
public class TwTradeLogEntity {
    private int id;
    private String tradeDate;
    private String stockId;
    private int quantity;
    private int payment;
}
