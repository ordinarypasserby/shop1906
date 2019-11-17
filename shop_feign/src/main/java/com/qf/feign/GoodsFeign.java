package com.qf.feign;

import com.qf.entity.Goods;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * @author DingYuHui
 * @Date 2019/10/10
 */
@FeignClient("web-goods")
public interface GoodsFeign {

    @RequestMapping("/goods/list")
    List<Goods> goodsList();

    @RequestMapping("/goods/insert")
    Boolean goodsInsert(@RequestBody Goods goods);

    @RequestMapping("/goods/queryById")
    Goods queryById(@RequestParam("gid") Integer gid);

    @RequestMapping("/goods/queryByTime")
    List<Map<String,Object>> querySeckillByTime();

    @RequestMapping("/backRollStock")
    @ResponseBody
    Boolean backRollStock(@RequestParam("gid") Integer gid);
}
