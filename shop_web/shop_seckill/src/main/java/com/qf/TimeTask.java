package com.qf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author DingYuHui
 * @Date 2019/11/5
 */
@Component
public class TimeTask {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 整点定时任务，秒杀开始
     */
    @Scheduled(cron = "0 0 0/1 * * *")
    public void seckillStart(){
        System.out.println("现在是琪亚娜时间");
    }
}
