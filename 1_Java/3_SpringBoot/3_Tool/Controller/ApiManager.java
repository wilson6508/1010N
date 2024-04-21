package com.manager;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ApiManager {

    @RequestMapping(value = "/insightApi/{module}", method = {RequestMethod.POST, RequestMethod.GET})
    public String keywordSearch(@PathVariable("module") String module, HttpServletRequest request) {
        if (!module.equals("")) {
            return "forward:/test/" + module;
        } else {
            return "";
        }
    }

}