package com.qf.service;

import com.qf.entity.Shopcart;
import com.qf.entity.User;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author DingYuHui
 * @Date 2019/10/22
 */
public interface ICartService {

    /**
     * 添加购物车
     * @param cartToken
     * @param gid
     * @param gnumber
     * @param user
     * @return
     */
    String insertCart(String cartToken, Integer gid, Integer gnumber, User user, BigDecimal price);

    /**
     * 查询购物车列表
     * @param cartToken
     * @param user
     * @return
     */
    List<Shopcart> queryShopCart(String cartToken, User user);

    /**
     * 合并购物车
     * @param cartToken
     * @param user
     * @return
     */
    int mergeShopCart(String cartToken, User user);
}
