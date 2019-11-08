package com.qf.util;

/**
 * @author DingYuHui
 * @Date 2019/11/5
 */
public interface ContactUtil {

    /**
     * redis中秒杀有序集合的key
     */
    String REDIS_SECKILL_SORT_SET = "seckill_sort";

    /**
     * redis中秒杀开始的集合的key
     */
    String REDIS_SECKILL_START_SET = "seckill_start";

    /**
     * 当前秒杀开始的时间后缀
     */
    String REDIS_SECKILL_TIME_PROFIX = "time_profix";


}
