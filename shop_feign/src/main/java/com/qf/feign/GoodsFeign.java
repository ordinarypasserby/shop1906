package com.qf.feign;

import com.qf.entity.Goods;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

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

    @RequestMapping("goods/queryById")
    Goods queryById(@RequestParam("gid") Integer gid);
}
