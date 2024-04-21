package com.manager;

import com.pojo.dto.ApiResponseBean;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class ApiResponseController {

    @RequestMapping(value = "/insightApiResponse", method = {RequestMethod.POST, RequestMethod.GET})
    public ResponseEntity<Object> insightApiResponseBean(HttpServletRequest request) {
        ApiResponseBean responseBean = (ApiResponseBean) request.getAttribute("response");
        return ResponseEntity.ok(responseBean);
    }

}