package com.qf.aop;

import java.lang.annotation.*;

/**
 * @author DingYuHui
 * @Date 2019/10/21
 */

@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface IsLogin {

    //方法的返回值类型 方法名() [default 默认值];
    boolean mustLogin() default false;
}
