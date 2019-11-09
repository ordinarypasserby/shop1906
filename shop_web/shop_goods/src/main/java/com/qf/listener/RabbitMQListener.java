package com.qf.listener;

import com.qf.service.IGoodsService;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

/**
 * @author DingYuHui
 * @Date 2019/11/8
 */
@Component
public class RabbitMQListener {

    @Autowired
    private IGoodsService goodsService;

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = "seckill_goods_queue",durable = "true",declare = "true"),
            exchange = @Exchange(name = "seckill_exchange",type = "fanout",declare = "true",durable = "true")
    ))
    public void msgHandler(Map<String,Object> map, Message message, Channel channel){

        //减少库存
        Integer gid = (Integer) map.get("gid");
        int flag = goodsService.reduceStocks(gid);

        if (flag > 0 ){
            //手动确认消息
            try {
                channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
