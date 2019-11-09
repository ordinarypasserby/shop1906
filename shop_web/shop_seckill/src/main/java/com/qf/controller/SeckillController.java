package com.qf.controller;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.qf.aop.IsLogin;
import com.qf.entity.User;
import com.qf.feign.GoodsFeign;
import com.qf.util.TimeUtil;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.imageio.ImageIO;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.*;

/**
 * @author DingYuHui
 * @Date 2019/11/4
 */
@Controller
@RequestMapping("/seckill")
public class SeckillController {

    @Autowired
    private GoodsFeign goodsFeign;
    @Autowired
    private DefaultKaptcha defaultKaptcha;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private RabbitTemplate rabbitTemplate;
    /**
     * 判断库存的lua方法
     */
    private String lua = "local gid = KEYS[1]\n" +
            "local orderid = ARGV[1]\n" +
            "local now = ARGV[2]\n" +
            "local save = tonumber(redis.call('get', 'seckill_save_'..gid))\n" +
            "\n" +
            "if save >= 1 then\n" +
            "\tredis.call('decr', 'seckill_save_'..gid)\t\n" +
            "\t--排队机制\n" +
            "\tredis.call('zadd', 'seckill_queue_'..gid, now, orderid)\n" +
            "\treturn 1\n" +
            "end\n" +
            "\n" +
            "return 0";


    @RequestMapping("/queryByTime")
    @ResponseBody
    public List<Map<String, Object>> queryByTime() {
        List<Map<String, Object>> maps = goodsFeign.querySeckillByTime();
        return maps;
    }

    /**
     * 获得当前服务器时间
     *
     * @return
     */
    @RequestMapping("/getNow")
    @ResponseBody
    public Date getNow() {
        return new Date();
    }

    @IsLogin(mustLogin = true)
    @RequestMapping("/rush")
    public String rush(Integer gid, User user, Model model) {
        System.out.println(user.getNickname() + "抢购了id为" + gid + "的商品！");

        //生成订单号
        String orderid = UUID.randomUUID().toString();
        String score = TimeUtil.dateSCore2(new Date());

        //判断库存
        Long execute = stringRedisTemplate.execute(new DefaultRedisScript<>(lua, Long.class),
                Collections.singletonList(gid + ""),
                orderid,
                score);

        if (execute == 0) {
            //库存不足
            return "error3";
        }

        //抢购成功，将消息放入mq，保证订单和库存的最终一致
        Map<String,Object> map = new HashMap<>();
        map.put("gid",gid);
        map.put("uid",user.getId());
        map.put("orderid",orderid);
        model.addAllAttributes(map);

        rabbitTemplate.convertAndSend("seckill_exchange","",map);

        return "succ";
    }

    /**
     * 获得验证码
     *
     * @param response
     */
    @RequestMapping("/getCode")
    @ResponseBody
    public void getCode(HttpServletResponse response) {

        //生成验证码，保存到服务器
        String text = defaultKaptcha.createText();
        String token = UUID.randomUUID().toString();
        stringRedisTemplate.opsForValue().set(token, text);

        //根据验证码生成图片
        BufferedImage image = defaultKaptcha.createImage(text);

        //将token写入cookie
        Cookie cookie = new Cookie("code_token", token);
        cookie.setMaxAge(5 * 60);
        cookie.setPath("/");
        response.addCookie(cookie);

        //将验证码写回客户端
        try {
            ImageIO.write(image, "jpg", response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
