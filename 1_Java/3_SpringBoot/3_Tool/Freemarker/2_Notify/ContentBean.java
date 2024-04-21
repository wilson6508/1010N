package com.eland.pojo.info;

import lombok.Data;

@Data
public class ContentBean {
    private String title;
    private String contentType;
    private String content;
    private String sidName;
    private String sareaName;
    private String sentimentTag;
    private String url;
    private int commentCount;
    private boolean isRestrictSource;
}