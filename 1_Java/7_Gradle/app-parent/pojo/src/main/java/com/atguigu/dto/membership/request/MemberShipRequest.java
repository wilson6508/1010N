package com.atguigu.dto.membership.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberShipRequest {
    private Model model;
    private Parameter parameter;
}
