package com.itheima.controller;

import com.itheima.pojo.Person;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DemoController {

    @GetMapping("/demo")
    public String demo(String name, Integer age, Model model) {
        model.addAttribute("name", name);
        model.addAttribute("sex", "男");
        model.addAttribute("age", age);
        return "pages/demo.jsp";
    }

    @GetMapping("/demoObj")
    public String demoObj(Person person, Model model) {
        person.setName(person.getName() + "AAA");
        model.addAttribute("person", person);
        return "pages/demoObj.jsp";
    }

}
