package com.qf.controller;

import com.qf.feign.GoodsFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author DingYuHui
 * @Date 2019/11/4
 */
@Controller
@RequestMapping("/seckill")
public class SeckillController {

    @Autowired
    private GoodsFeign goodsFeign;

    @RequestMapping("/queryByTime")
    @ResponseBody
    public List<Map<String,Object>> queryByTime(){
        List<Map<String, Object>> maps = goodsFeign.querySeckillByTime();
        return maps;
    }

    /**
     * 获得当前服务器时间
     * @return
     */
    @RequestMapping("/getNow")
    @ResponseBody
    public Date getNow(){
        return new Date();
    }
}
