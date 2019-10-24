package com.qf.controller;

import com.qf.aop.IsLogin;
import com.qf.entity.Address;
import com.qf.entity.User;
import com.qf.service.IAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author DingYuHui
 * @Date 2019/10/23
 */
@Controller
@RequestMapping("/address")
public class AddressController {
    @Autowired
    private IAddressService addressService;

    /**
     * 添加收货地址
     * @param address
     * @param user
     * @return
     */
    @IsLogin(mustLogin = true)
    @RequestMapping("/insert")
    @ResponseBody
    public String inert(Address address, User user){
        System.out.println("添加收获地址："+address);
        address.setUid(user.getId());
        addressService.insertAddress(address);
        return "succ";
    }

}
