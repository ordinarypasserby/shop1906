package com.qf.service;

import com.qf.entity.Goods;

import java.util.List;

/**
 * @author DingYuHui
 * @Date 2019/10/10
 */
public interface IGoodsService {
    List<Goods> queryAllGoods();

    //添加商品
    int insertGoods(Goods goods);

    /**
     * 根据商品id查询商品详情
     * @param gid
     * @return
     */
    Goods queryById(Integer gid);
}
