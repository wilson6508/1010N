package com.atguigu.controller;

import com.atguigu.clients.MemberShipClient;
import com.atguigu.dto.membership.request.MemberShipRequest;
import com.atguigu.dto.membership.request.Model;
import com.atguigu.dto.membership.request.NotifyConfigInfo;
import com.atguigu.dto.membership.request.Parameter;
import com.atguigu.dto.membership.response.MembershipResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class TestController {

    @Resource
    private MemberShipClient memberShipClient;

    @GetMapping("/test")
    public String test() {
        return "test";
    }

    @GetMapping("/demo")
    public MembershipResponse demo() {
        MemberShipRequest memberShipRequest = new MemberShipRequest();
        memberShipRequest.setModel(new Model("read_notify_task"));
        memberShipRequest.setParameter(new Parameter(new NotifyConfigInfo()));
        return memberShipClient.getRep(memberShipRequest);
    }

}
