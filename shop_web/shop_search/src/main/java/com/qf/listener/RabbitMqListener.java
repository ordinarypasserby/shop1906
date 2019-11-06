package com.qf.listener;

import com.qf.entity.Goods;
import com.qf.service.ISearchService;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author DingYuHui
 * @Date 2019/10/16
 */
@Component
public class RabbitMqListener {

    @Autowired
    private ISearchService searchService;

    @RabbitListener(bindings = @QueueBinding(
            exchange = @Exchange(name = "goods_exchange",durable = "true",type = "direct"),
            value = @Queue(name = "search_queue",durable = "true"),
            key = {"normal","seckill"}))
    public void goodsMsgHandler(Goods goods){
        System.out.println("接收到RabbitMQ的消息！，并且同步到索引库中");
        //同步索引库
        searchService.insert(goods);
    }
}
