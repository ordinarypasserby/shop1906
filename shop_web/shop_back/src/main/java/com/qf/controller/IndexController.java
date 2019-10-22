package com.qf.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author DingYuHui
 * @Date 2019/10/9
 */
@Controller
public class IndexController {

    @RequestMapping("/tohome")
    public String toHome(){
        return "home";
    }
}

