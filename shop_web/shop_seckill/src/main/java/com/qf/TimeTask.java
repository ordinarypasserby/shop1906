package com.qf;

import com.qf.util.ContactUtil;
import com.qf.util.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

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

        //更新时间后缀
        String profix = TimeUtil.dateSCore(new Date());

        //更新当前时间标识
        stringRedisTemplate.opsForValue().set(ContactUtil.REDIS_SECKILL_TIME_PROFIX,profix);

        //删除上个时间段秒杀商品的集合
        Date agoTime = TimeUtil.getNext(-1);
        String agoProfix = TimeUtil.dateSCore(agoTime);
        stringRedisTemplate.delete(ContactUtil.REDIS_SECKILL_START_SET + "_" + agoProfix);

        //删除缓存
        stringRedisTemplate.delete("seckill::indexList");
    }
}
