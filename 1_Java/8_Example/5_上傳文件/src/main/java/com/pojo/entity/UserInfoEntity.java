package com.pojo.entity;

import lombok.Data;

@Data
public class UserInfoEntity {
    private Integer id;
    private Integer userId;
    private String userInfo;
    private String result;
    private String createTime;
    private String updateTime;
}
