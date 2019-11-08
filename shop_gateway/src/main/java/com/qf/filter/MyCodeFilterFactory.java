package com.qf.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;

/**
 * @author DingYuHui
 * @Date 2019/11/6
 */
@Component
public class MyCodeFilterFactory extends AbstractGatewayFilterFactory {

    @Autowired
    private MyCodeFilter myCodeFilter;


    @Override
    public GatewayFilter apply(Object config) {
        return myCodeFilter;
    }

    /**
     * 设置过滤器的名字
     * @return
     */
    @Override
    public String name() {
        return "myCode";
    }
}
