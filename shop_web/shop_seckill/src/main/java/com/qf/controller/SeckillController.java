package com.qf.controller;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.qf.aop.IsLogin;
import com.qf.entity.User;
import com.qf.feign.GoodsFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.imageio.ImageIO;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

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

    @RequestMapping("/queryByTime")
    @ResponseBody
    public List<Map<String,Object>> queryByTime(){
        List<Map<String, Object>> maps = goodsFeign.querySeckillByTime();
        return maps;
    }

    /**
     * 获得当前服务器时间
     * @return
     */
    @RequestMapping("/getNow")
    @ResponseBody
    public Date getNow(){
        return new Date();
    }

    @IsLogin(mustLogin = true)
    @RequestMapping("/rush")
    public String rush(Integer gid, User user){
        System.out.println(user.getNickname() + "抢购了id为" + gid + "的商品！");
        return "succ";
    }

    /**
     * 获得验证码
     * @param response
     */
    @RequestMapping("/getCode")
    @ResponseBody
    public void getCode(HttpServletResponse response){

        //生成验证码，保存到服务器
        String text = defaultKaptcha.createText();
        String token = UUID.randomUUID().toString();
        stringRedisTemplate.opsForValue().set(token,text);

        //根据验证码生成图片
        BufferedImage image = defaultKaptcha.createImage(text);

        //将token写入cookie
        Cookie cookie = new Cookie("code_token",token);
        cookie.setMaxAge(5 * 60);
        cookie.setPath("/");
        response.addCookie(cookie);

        //将验证码写回客户端
        try {
            ImageIO.write(image,"jpg",response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
