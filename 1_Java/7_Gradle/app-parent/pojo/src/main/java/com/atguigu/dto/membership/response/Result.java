package com.atguigu.dto.membership.response;

import lombok.Data;

@Data
public class Result {
    // 報表資訊
    private Integer id;
    private Integer serviceId;
    private String configName;
    private String type;
    private Boolean enable;
    // 報表主題&來源
    private String topic;
    private Integer mainType;
    private String sourceList;
    private String sourceClusterList;
    // 文章列表搜尋條件
    private Integer hitNumFilter;
    private Integer commentCountFilter;
    private Double posThresholdFilter;
    private Double negThresholdFilter;
    private Boolean deDuplicateFilter;
    private String orderType;
    // 報表自訂篩選
    private String dimensionList;
    private String audienceList;
    private String exclusionClusterList;
    // 特殊設定
    private String contentSection;
    private Integer maxRecord;
    private Integer dataTimeSetting;
    private Integer isCustom;
    // 發報方式
    private String lineGroupId;
    private String telegramGroupId;
    private String mailList;
    private int sendFreq;
    // 時間
    private String startTime;
    private String endTime;
    private String createTime;
    private String updateTime;
    // 關鍵字相關
    private String typeName;
    private String name;
    private String keywords;
}
