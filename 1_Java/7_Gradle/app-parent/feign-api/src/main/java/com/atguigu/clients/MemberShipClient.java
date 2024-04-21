package com.atguigu.clients;

import com.atguigu.dto.membership.request.MemberShipRequest;
import com.atguigu.dto.membership.response.MembershipResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "MemberShip", url = "https://release-test1.opview.com.tw")
public interface MemberShipClient {

    @PostMapping("/insightApi/internal")
    MembershipResponse getRep(@RequestBody MemberShipRequest memberShipRequest);

}
