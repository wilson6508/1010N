package com.atguigu.dto.membership.response;

import lombok.Data;

@Data
public class ResponseInfo {
    private Double version;
    private Double error_code;
    private String error_message;
    private Double query_time;
}
