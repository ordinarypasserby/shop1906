package com.qf.feign;

import com.qf.entity.Shopcart;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author DingYuHui
 * @Date 2019/10/23
 */
@FeignClient("web-cart")
public interface CartFeign {

    @RequestMapping("/cart/queryByGid")
    public List<Shopcart> queryByGid(@RequestParam("gid") Integer[] gid, @RequestParam("uid") Integer uid);

    @RequestMapping("/cart/queryByIds")
    List<Shopcart> queryByIds(@RequestParam("ids") Integer[] cartids);

    /**
     * 根据购物车id列表删除购物车信息
     * @param ids
     * @return
     */
    @RequestMapping("/cart/deleteByIds")
    boolean deleteByIds(@RequestParam("ids") Integer[] ids);
}
