package com.qf.service;

import com.qf.entity.Shopcart;
import com.qf.entity.User;

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
    String insertCart(String cartToken, Integer gid, Integer gnumber, User user);

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

    /**
     * 根据商品id，用户id查询购物车信息
     * @param gid
     * @param uid
     * @return
     */
    List<Shopcart> queryByGid(Integer[] gid, Integer uid);

    /**
     * 根据购物车的id数组，查询购物车列表
     * @param ids
     * @return
     */
    List<Shopcart> queryByIds(Integer[] ids);

    /**
     * 根据购物车id列表删除购物车
     * @param ids
     * @return
     */
    int deleteByIds(Integer[] ids);
}
