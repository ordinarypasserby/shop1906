package com.qf.shop_item;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author DingYuHui
 * @Date 2019/10/16
 */
@Configuration
public class RabbitMQConfig {

    /*
    创建队列
     */
//    @Bean("search_queue")
//    public Queue getQueue1(){
//
//        return new Queue("search_queue");
//    }
//
//    @Bean("item_queue")
//    public Queue getQueue2(){
//
//        return new Queue("item_queue");
//    }

    /*
    创建交换机
     */
    @Bean("goods_exchange")
    public DirectExchange getExchange(){

        return new DirectExchange("goods_exchange");
    }

    /*
    交换机和队列进行绑定
     */
//    @Bean
//    public Binding getBinding1(Queue search_queue,FanoutExchange goods_exchange){
//
//        return BindingBuilder.bind(search_queue).to(goods_exchange);
//    }
//
//    @Bean
//    public Binding getBinding2(Queue item_queue,FanoutExchange goods_exchange){
//
//        return BindingBuilder.bind(item_queue).to(goods_exchange);
//    }


}
