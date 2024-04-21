package com.atguigu.dto.membership.response;

import lombok.Data;

import java.util.List;

@Data
public class MembershipResponse {
    private ResponseInfo response_info;
    private List<Result> result;
}
