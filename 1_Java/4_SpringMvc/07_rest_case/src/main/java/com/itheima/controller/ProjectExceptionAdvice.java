package com.itheima.controller;

import com.itheima.pojo.exception.BusinessException;
import com.itheima.pojo.exception.SystemException;
import com.itheima.pojo.dto.Result;
import com.itheima.util.Code;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ProjectExceptionAdvice {

    @ExceptionHandler(BusinessException.class)
    public Result handleBusinessException(BusinessException ex) {
        return new Result(ex.getCode(), ex.getMessage(), null);
    }

    @ExceptionHandler(SystemException.class)
    public Result handleSystemException(SystemException ex) {
        // log
        // 通知維運
        // 通知開發
        return new Result(ex.getCode(), ex.getMessage(), null);
    }

    @ExceptionHandler(Exception.class)
    public Result handleException(Exception ex) {
        // log
        // 通知維運
        // 通知開發
        return new Result(Code.SYSTEM_UNKNOWN_ERR, "系統錯誤", null);
    }

}
