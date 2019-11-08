package com.qf.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author DingYuHui
 * @Date 2019/11/7
 */
@Controller
@RequestMapping("/info")
public class FrontController {

    @RequestMapping("/error")
    public String error(){
        return "error";
    }
}
