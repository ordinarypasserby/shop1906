package com.qf.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author DingYuHui
 * @Date 2019/11/4
 */
public class TimeUtil {
    /**
     * 查询当前的整点
     */
    public static Date getNow(){

        return getNext(0);
    }

    /**
     * 获得指定后续的整点时间
     */
    public static Date getNext(int next){
        //获得当前的日历时间
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR_OF_DAY,next);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.SECOND,0);
        calendar.set(Calendar.MILLISECOND,0);
        return calendar.getTime();
    }

    /**
     * 根据时间计算出当前整点对应的时间字符串
     * @param date
     * @return
     */
    public static String dateSCore(Date date){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyMMddHH");
        String format = simpleDateFormat.format(date);
        return format;
    }

}
