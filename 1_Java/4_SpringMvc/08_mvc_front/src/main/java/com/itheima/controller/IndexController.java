package com.itheima.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class IndexController {

    @RequestMapping("/index")
    public ModelAndView index(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("pages/index.jsp");
        modelAndView.addObject("name","派大星");
        modelAndView.addObject("sex","男");
        modelAndView.addObject("age",53);
        return modelAndView;
    }

}
