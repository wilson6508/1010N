package com.controller;

import com.pojo.dto.ApiResponseBean;
import com.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
public class TestController {

    @Autowired
    private TestService testService;

    @RequestMapping(value = "/test/caGetTopics", method = RequestMethod.POST)
    public String pgGetTopics(HttpServletRequest request) {
        ApiResponseBean responseBean = testService.getApiResponseBean();
        request.setAttribute("response", responseBean);
        return "forward:/insightApiResponse";
    }

}