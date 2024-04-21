package com.feign.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "helloService", url = "${project.url}")
public interface HelloClient {

    @GetMapping("/")
    String getHelloMessage();

}
