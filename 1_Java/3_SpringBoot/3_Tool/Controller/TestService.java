package com.service;

import com.pojo.dto.ApiResponseBean;
import org.springframework.stereotype.Service;

@Service
public class TestService {

    public ApiResponseBean getApiResponseBean() {
        ApiResponseBean apiResponseBean = new ApiResponseBean();
        apiResponseBean.setSuccess(true);
        apiResponseBean.setMessage("測試");
        apiResponseBean.setErrorCode("7.0");
        apiResponseBean.setData("HELLO");
        return apiResponseBean;
    }

}