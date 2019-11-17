package com.qf.task;

import com.alibaba.fastjson.JSONObject;
import com.qf.util.TimeUtil;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Set;

/**
 * @author DingYuHui
 * @Date 2019/11/13
 */
@Component
public class RemindTask {

    @Autowired
    private StringRedisTemplate redisTemplate;
    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 任意一个小时50分时都会触发
     */
    @Scheduled(cron = "0 50 * * * *")
    public void remindTime(){
        //计算当前时间的评分
        String score = TimeUtil.dateSCore(new Date());
        //根据评分找出redis中需要提醒的消息信息
        Set<String> seckillRemind = redisTemplate.opsForZSet().rangeByScore("seckill_remind", Double.valueOf(score), Double.valueOf(score));

        //删除提醒消息
        redisTemplate.opsForZSet().removeRangeByScore("seckill_remind", Double.valueOf(score), Double.valueOf(score));

        //处理提醒消息
        if (seckillRemind != null && seckillRemind.size()>0){
            for (String remindJson : seckillRemind) {
                JSONObject jsonObject = JSONObject.parseObject(remindJson);
                jsonObject.put("msgType",3);
                //发送到rabbitmq中
                rabbitTemplate.convertAndSend("netty_exchange",jsonObject.toString());
//                Map map = JSON.parseObject(remindJson, HashMap.class);
//
//                //提醒的商品id
//                Integer gid = (Integer) map.get("gid");
//                Integer uid = (Integer) map.get("uid");

                //将这个提醒的消息封装成一个消息对象 -> rabbitmq -> netty ->websocket -> 客户端

            }
        }
    }
}
