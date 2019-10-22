package com.qf.controller;

import com.qf.entity.Goods;
import com.qf.service.IGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author DingYuHui
 * @Date 2019/10/10
 */
@Controller
@RequestMapping("/goods")
public class GoodsController {
    @Autowired
    private IGoodsService goodsService;

    /**
     * 查询所有商品列表
     * @return
     */
    @RequestMapping("/list")
    @ResponseBody
    public List<Goods> goodsList(){
        List<Goods> goods = goodsService.queryAllGoods();
        return goods;
    }

    /**
     * 添加商品
     * @param goods
     * @return
     */
    @RequestMapping("/insert")
    @ResponseBody
    public Boolean goodsInsert(@RequestBody Goods goods){
        int result = goodsService.insertGoods(goods);
        return result>0;
    }
}
