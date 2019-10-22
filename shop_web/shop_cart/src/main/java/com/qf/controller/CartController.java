package com.qf.controller;

import com.qf.aop.IsLogin;
import com.qf.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author DingYuHui
 * @Date 2019/10/21
 */

@Controller
@RequestMapping("/cart")
public class CartController {

    @IsLogin
    @RequestMapping("/insert")
    public String insert(Integer gid, Integer gnumber, User user){
        System.out.println("添加购物车：" + gid + " " + gnumber);
        System.out.println("当前是否登录：" + user);

        //判断当前用户是否登录？

        return "succ";
    }
}
