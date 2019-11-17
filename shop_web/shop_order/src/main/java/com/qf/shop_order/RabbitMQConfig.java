package com.qf.shop_order;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @author DingYuHui
 * @Date 2019/11/9
 * 创建死信队列交换机
 */
@Configuration
public class RabbitMQConfig {

    /**
     * 创建order死信交换机
     * @return
     */
    @Bean
    public FanoutExchange getDelayExchange(){

        return new FanoutExchange("order_delay_exchange");
    }

    /**
     * 创建一个死信消息队列
     * @return
     */
    @Bean
    public Queue getDelayQueue(){

        return new Queue("order_delay_queue",true,false,false,null);
    }

    /**
     *将死信消息和死信队列进行绑定
     */
    @Bean
    public Binding getDelayBinding(Queue getDelayQueue, FanoutExchange getDelayExchange){

        return BindingBuilder.bind(getDelayQueue).to(getDelayExchange);
    }


    /**
     * 创建订单的普通消息对列
     * @return
     */
    @Bean
    public Queue getNormalQueue(){
        Map<String,Object> map = new HashMap();
        map.put("x-dead-letter-exchange","order_delay_exchange");
        map.put("x-message-ttl", 60000);//设置队列的过期时间，产生死信消息;
        return new Queue("order_normal_queue",true,false,false,map);
    }

    /**
     * 创建一个普通的交换机，设置该交换机的过期时间，同时该改交换机不需要消费者
     * @return
     */
    @Bean
    public FanoutExchange getNormalExchange(){

        return new FanoutExchange("order_normal_exchange",false,false,null);
    }

    @Bean
    public Binding getNormalBinding(Queue getNormalQueue, FanoutExchange getNormalExchange){

        return BindingBuilder.bind(getNormalQueue).to(getNormalExchange);
    }




}
