package com.qf.controller;

import com.qf.aop.IsLogin;
import com.qf.entity.Address;
import com.qf.entity.Orders;
import com.qf.entity.Shopcart;
import com.qf.entity.User;
import com.qf.feign.CartFeign;
import com.qf.service.IAddressService;
import com.qf.service.IOrderService;
import com.qf.util.PriceUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Arrays;
import java.util.List;

/**
 * @author DingYuHui
 * @Date 2019/10/23
 */
@Controller
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private IOrderService orderService;
    @Autowired
    private CartFeign cartFeign;
    @Autowired
    private IAddressService addressService;

    /**
     * 进入订单编辑页面
     * @param cart_checkbox
     * @param user
     * @param model
     * @return
     */
    @IsLogin(mustLogin = true)
    @RequestMapping("/edit")
   public String orderEdit(Integer[] cart_checkbox, User user, Model model){
        //下单的商品id
        System.out.println(user + "当前需要下单的商品id："+ Arrays.toString(cart_checkbox));

        //获得对应的购物车信息
        List<Shopcart> shopcarts = cartFeign.queryByGid(cart_checkbox, user.getId());

        //获得当前用户的收获地址信息
        List<Address> addresses = addressService.queryByUid(user.getId());

        //根据购物车信息，计算出订单总价
        double allprice = PriceUtil.allprice(shopcarts);

        model.addAttribute("shopcarts", shopcarts);
        model.addAttribute("addresses", addresses);
        model.addAttribute("allprice", allprice);
        return "orderedit";
    }

    /**
     * 添加订单
     * @param aid
     * @param cartids
     * @param user
     * @return
     */
    @IsLogin(mustLogin = true)
    @RequestMapping("/insert")
    public String orderInsert(Integer aid,Integer[] cartids,User user){

        orderService.insertOrder(aid,cartids,user.getId());

        //去支付
        return "pay";
    }

    /**
     * 查看订单列表
     * @param user
     * @param model
     * @return
     */
    @IsLogin(mustLogin = true)
    @RequestMapping("/list")
    public String orderList(User user, Model model){
        System.out.println("进来了");
        List<Orders> orders = orderService.queryByUid(user.getId());
        model.addAttribute("ordersList", orders);

        return "orderlist";
    }
}
