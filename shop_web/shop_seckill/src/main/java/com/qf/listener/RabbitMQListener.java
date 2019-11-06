package com.qf.listener;

import com.qf.entity.Goods;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.FileWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

/**
 * @author DingYuHui
 * @Date 2019/11/5
 */
@Component
public class RabbitMQListener {

    @Autowired
    private Configuration configuration;

    @RabbitListener(bindings = @QueueBinding(
            exchange = @Exchange(name = "goods_exchange",type = "direct",durable = "true"),
            value = @Queue("seckill_queue"),
            key = "seckill"))
    public void msgHandler(Goods goods){
        System.out.println("秒杀服务收到生成秒杀静态页的消息：" + goods);

        //准备一个输入路径
        //获得classPath路径
        String path = RabbitMQListener.class.getResource("/static/seckill").getPath();

        //通过freemarker生成静态页
        try (
                Writer writer = new FileWriter(path + "/" + goods.getId() + ".html");
            ){
            Template template = configuration.getTemplate("seckill.ftl");

            //准备数据
            Map<String,Object> map = new HashMap<>();
            map.put("goods",goods);

            //生成静态页
            template.process(map,writer);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
