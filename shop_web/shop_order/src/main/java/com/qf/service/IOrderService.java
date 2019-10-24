package com.qf.service;

import com.qf.entity.Orders;

import java.util.List;

/**
 * @author DingYuHui
 * @Date 2019/10/23
 */
public interface IOrderService {

    int  insertOrder(Integer aid, Integer[] cartids, Integer uid);

    /**
     * 查看订单列表
     * @param uid
     * @return
     */
    List<Orders> queryByUid(Integer uid);
}
