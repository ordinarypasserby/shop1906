package com.qf.service;

import com.github.pagehelper.PageInfo;
import com.qf.entity.Goods;
import com.qf.entity.Page;

import java.util.List;

/**
 * @author DingYuHui
 * @Date 2019/10/15
 */
public interface ISearchService  {

    //添加商品到索引库
    boolean insert(Goods goods);

    //根据关键字搜索索引库
    List<Goods> query(String keyword);

    //根据关键字搜索索引库并分页
    PageInfo<Goods> query(Page page, String keyword);
}
