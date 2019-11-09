package com.qf.service;

import com.qf.entity.Goods;

import java.util.List;
import java.util.Map;

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

    List<Map<String, Object>> querySeckillByTime();

    int reduceStocks(Integer gid);
}
