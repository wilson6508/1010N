package com.itheima.controller.utils;

//import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

// 作為SpringMVC的異常處理器
//@ControllerAdvice
@RestControllerAdvice
public class ProjectExceptionAdvice {

    @ExceptionHandler
    public R doException(Exception ex) {
        // console日誌
        ex.printStackTrace();
        // 儲存日誌
        // db.saveLog
        // 返回數據
        return new R("服務器異常");
    }

//    @ExceptionHandler(RuntimeException.class)

}
