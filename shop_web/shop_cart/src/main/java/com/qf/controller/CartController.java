package com.qf.controller;

import com.qf.aop.IsLogin;
import com.qf.entity.Shopcart;
import com.qf.entity.User;
import com.qf.service.ICartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author DingYuHui
 * @Date 2019/10/21
 * 登陆的购物车
 * 往数据库中存储
 *
 * 未登录的购物车
 * 往cookie + redis中存储
 */

@Controller
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private ICartService cartService;

    /**
     * 添加到购物车
     * @param cartToken
     * @param gid
     * @param gnumber
     * @param user
     * @param response
     * @return
     */
    @IsLogin
    @RequestMapping("/insert")
    public String insert(@CookieValue(value = "cart_token",required = false) String cartToken, Integer gid, Integer gnumber, User user, HttpServletResponse response){
        System.out.println("添加购物车：" + gid + " " + gnumber);
        System.out.println("当前是否登录：" + user);

        //添加到购物车并判断当前用户是否登录？
        cartToken = cartService.insertCart(cartToken,gid,gnumber,user);

        //将返回的cartToken添加到cookie中国

        Cookie cookie = new Cookie("cart_token",cartToken);
        cookie.setMaxAge(60 * 60 * 24 * 365);
        cookie.setPath("/");
        response.addCookie(cookie);

        return "succ";
    }

    /**
     * 查询所有购物车列表
     * @param cartToken
     * @param user
     * @return
     */
    @IsLogin
    @RequestMapping("/list")
    @ResponseBody
    public List<Shopcart> queryShopCart(@CookieValue(value = "cart_token",required = false) String cartToken,User user){

        List<Shopcart> shopcarts = cartService.queryShopCart(cartToken,user);

        return shopcarts;
    }

    /**
     * 合并购物车
     * @param cartToken
     * @param user
     * @param response
     * @return
     */
    @IsLogin
    @RequestMapping("/merge")
    public String mergeShopCart(@CookieValue(value = "cart_token",required = false) String cartToken,String returnUrl,User user,HttpServletResponse response){
        int result = cartService.mergeShopCart(cartToken,user);
        if (result > 0){
            //合并完成，删除临时购物车的cookie
            Cookie cookie = new Cookie("cart_token",null);
            cookie.setMaxAge(0);
            cookie.setPath("/");
            response.addCookie(cookie);
        }

        return "redirect:"+returnUrl;
    }

    @IsLogin
    @RequestMapping("/showlist")
    public String showlist(@CookieValue(value = "cart_token",required = false) String cartToken, User user, Model model){
        List<Shopcart> shopcarts = cartService.queryShopCart(cartToken, user);
        model.addAttribute("shopcarts",shopcarts);
        return "cartlist";
    }

    /**
     * 根据商品id，用户id查询购物车信息
     * @param gid
     * @param uid
     * @return
     */
    @RequestMapping("/queryByGid")
    @ResponseBody
    public List<Shopcart> queryByGid(@RequestParam("gid") Integer[] gid, @RequestParam("uid") Integer uid){

        return cartService.queryByGid(gid,uid);
    }

    /**
     * 根据购物车的id数组查询购物车列表
     * @param ids
     * @return
     */
    @RequestMapping("/queryByIds")
    @ResponseBody
    public List<Shopcart> queryByIds(@RequestParam("ids") Integer[] ids){
        return cartService.queryByIds(ids);
    }

    /**
     * 根据购物车id删除购物车信息
     * @return
     */
    @RequestMapping("/deleteByIds")
    @ResponseBody
    public boolean deleteByIds(@RequestParam("ids") Integer[] ids){
        return cartService.deleteByIds(ids) > 0;
    }
}
