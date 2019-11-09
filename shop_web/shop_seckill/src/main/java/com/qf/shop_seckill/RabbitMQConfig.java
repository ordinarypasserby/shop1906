package com.qf.shop_seckill;

import org.springframework.amqp.core.FanoutExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author DingYuHui
 * @Date 2019/11/8
 */
@Configuration
public class RabbitMQConfig {

    @Bean
    public FanoutExchange getExchange(){
        return new FanoutExchange("seckill_exchange");
    }
}
