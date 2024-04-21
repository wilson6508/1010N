package com.pojo.entity;

import lombok.Data;

@Data
public class SearchLogEntity {
    private int id;
    private String frontName;
    private String modelName;
    private String queryJson;
    private String result;
    private String createTime;
}
