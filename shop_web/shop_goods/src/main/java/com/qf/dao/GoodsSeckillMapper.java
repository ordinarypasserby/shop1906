package com.qf.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qf.entity.GoodsSeckill;

import java.util.List;

/**
 * @author DingYuHui
 * @Date 2019/11/4
 */
public interface GoodsSeckillMapper extends BaseMapper<GoodsSeckill> {

    List<GoodsSeckill> queryNow();

    List<GoodsSeckill> queryNext();

    int reduceStocks(Integer gid);

    int backRollStock(Integer gid);
}
