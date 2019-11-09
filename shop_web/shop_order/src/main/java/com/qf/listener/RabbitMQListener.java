package com.qf.listener;

import com.qf.entity.Orders;
import com.qf.service.IOrderService;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
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
    private IOrderService orderService;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;


    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = "seckill_orders_queue",durable = "true",declare = "true"),
            exchange = @Exchange(name = "seckill_exchange",type = "fanout",declare = "true",durable = "true")
    ))
    public void msghandler(Map<String,Object> map, Message message, Channel channel){

        Integer gid = (Integer) map.get("gid");
        Integer uid = (Integer) map.get("uid");
        String orderid = (String) map.get("orderid");

        //生成秒杀订单
        Orders orders = orderService.insertSeckillOrder(gid, uid, orderid);

        //删除队列中的这个订单的消息
        stringRedisTemplate.opsForZSet().remove("seckill_queue_"+gid+orderid);

        if (orderid != null){
            //手动确认消息
            try {
                channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
