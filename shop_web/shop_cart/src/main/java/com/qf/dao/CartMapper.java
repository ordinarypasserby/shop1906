package com.qf.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qf.entity.Shopcart;

import java.util.List;

/**
 * @author DingYuHui
 * @Date 2019/10/22
 */
public interface CartMapper extends BaseMapper<Shopcart> {

    List<Shopcart> queryByGid(Integer[] gid, Integer uid);
}
