package com.qf.controller;

import com.alibaba.fastjson.JSON;
import com.qf.aop.IsLogin;
import com.qf.entity.Goods;
import com.qf.entity.User;
import com.qf.feign.GoodsFeign;
import com.qf.util.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author DingYuHui
 * @Date 2019/11/13
 */
@Controller
@RequestMapping("/msg")
public class MsgController {
    @Autowired
    private GoodsFeign goodsFeign;
    @Autowired
    private StringRedisTemplate redisTemplate;
    /**
     * 秒杀提醒
     */
    @IsLogin(mustLogin = true)
    @RequestMapping("/remind")
    @ResponseBody
    public String remind(Integer gid,User user){
        System.out.println(user.getId()+"-"+user.getNickname() +"   设置秒杀提醒："+gid);

        Map<String,Object> map = new HashMap<>();
        map.put("gid",gid);
        map.put("uid",user.getId());



        //获得商品的开抢时间
        Goods goods = goodsFeign.queryById(gid);
        Date startTime = goods.getGoodsSeckill().getStartTime();
        //根据开抢时间计算前10分钟的时间
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startTime);
        calendar.add(Calendar.MINUTE,-10);
        Date remindTime = calendar.getTime();
        //计算出评分
        String score = TimeUtil.dateSCore(remindTime);

        //将提醒信息放入redis中
        redisTemplate.opsForZSet().add("seckill_remind", JSON.toJSONString(map),Double.valueOf(score ));

        return "succ";
    }

    /**
     * 取消预约
     * @param request
     * @return
     */
    @RequestMapping("/cancelRemind")
    @ResponseBody
    public String cancelRemind(HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if (cookie.getName() == "remind"){
                cookie.setMaxAge(0);
            }
        }
        //删除reids提醒信息
        return "dele";
    }
}
