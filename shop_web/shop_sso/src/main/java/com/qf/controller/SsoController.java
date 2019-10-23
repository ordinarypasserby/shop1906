package com.qf.controller;

import com.qf.entity.Email;
import com.qf.entity.NoteCode;
import com.qf.entity.ResultData;
import com.qf.entity.User;
import com.qf.service.IUserService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author DingYuHui
 * @Date 2019/10/18
 */
@Controller
@RequestMapping("/sso")
public class SsoController {

    @Autowired
    private IUserService userService;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 跳转到注册页面
     * @return
     */
    @RequestMapping("/toRegister")
    public String toRegister(){

        return "register";
    }

    /**
     * 跳转至登陆页面
     * @return
     */
    @RequestMapping("/toLogin")
    public String toLogin(){

        return "login";
    }

    /**
     * 注册用户
     * @param user
     * @return
     */
    @RequestMapping("/register")
    public String register(User user, Model model){
        int result = userService.register(user);
        if(result > 0){
            //注册成功，跳转到登陆界面
            return "login";
        }
        if(result == -1){
            model.addAttribute("erro","用户名已经存在");
        }else {
            model.addAttribute("erro","注册失败，请联系管理员");
        }

        return "register";
    }

    /**
     * 跳转至忘记密码界面
     * @return
     */
    @RequestMapping("/toForgetPassword")
    public String toForgetPassword(){

        return "forgetpasswordType";
    }

    /**
     * 跳转到邮箱验证
     * @return
     */
    @RequestMapping("/toForgetPasswordByEmail")
    public String toFindPasswordByEmail(){

        return "forgetpassword";
    }

    /**
     * 跳转到手机验证码认证
     * @return
     */
    @RequestMapping("/toForgetPasswordByNoteCode")
    public String toForgetPasswordByNoteCode(){

        return "forgetPasswordByNoteCode";
    }

    /**
     * 找回密码
     * @param username
     * @return
     */
    @RequestMapping("/findPassword")
    @ResponseBody
    public ResultData findPassword(String username){
        System.out.println("需要找回密码的账号：" + username);

        //调用业务层通过用户名找到用户信息 - 用户的邮箱
        User user = userService.queryByUserName(username);
        if(user == null){
            //说明用户名不存在
            return new ResultData().setCode("1000").setMsg("用户名不存在！");
        }

        //再给用户的邮箱发送一个找回密码的邮件
        //1、时效性
        //1、一次性
        //1、不能拿过来改其他人的密码
        String token = UUID.randomUUID().toString();
        stringRedisTemplate.opsForValue().set(token, username);
        stringRedisTemplate.expire(token, 5, TimeUnit.MINUTES);

        String url = "http://localhost:16666/sso/toUpdatePassword?token=" + token;
        String content = "点击如下链接，找回密码：<a href='" + url + "'>" + url + "</a>";


        Email email = new Email()
                .setSubject("京北商城找回密码邮件！")
                .setTo(user.getEmail())
                .setContent(content);
        rabbitTemplate.convertAndSend("mail_exchange", "", email);

        //准备参数
        Map<String, String> data = new HashMap<>();

        //获得邮箱中间的内容
        String emailMiddleInfo = user.getEmail().substring(3, user.getEmail().indexOf("@"));
        String mailInfo = user.getEmail().replace(emailMiddleInfo, "******");
        data.put("mailInfo", mailInfo);

        //获得邮箱的跳转页面
        String emailUrl = "http://mail." + user.getEmail().substring(user.getEmail().indexOf("@") + 1);
        data.put("emailUrl", emailUrl);

        return new ResultData().setCode("0000").setMsg("邮件发送成功！").setData(data);
    }

    /**
     * 短信验证
     * @param username
     * @return
     */
    @RequestMapping("/findPasswordByNoteCode")
    @ResponseBody
    public ResultData findPasswordByNoteCode(String username){
        System.out.println("需要找回密码的账号：" + username);

        //调用业务层通过用户名找到用户信息 - 用户的邮箱
        User user = userService.queryByUserName(username);
        if(user == null){
            //说明用户名不存在
            return new ResultData().setCode("1000").setMsg("用户名不存在！");
        }
        //rabbitTemplate.convertAndSend("phoneCode_exchange", "", phone);
        NoteCode noteCode = new NoteCode();
        noteCode.setPhoneNumbers(user.getPhone());
        noteCode.setCode((int)(Math.random()*9999)+100);
        rabbitTemplate.convertAndSend("notecode_exchange", "", noteCode);
        //存入redis
        String token = UUID.randomUUID().toString();
        stringRedisTemplate.opsForValue().set(token,noteCode.getCode().toString());
        stringRedisTemplate.expire(token,1,TimeUnit.MINUTES);

        //stringRedisTemplate.opsForValue().set(username,noteCode.getCode().toString());
       // stringRedisTemplate.expire(username,1,TimeUnit.MINUTES);
        //准备参数
        Map<String, String> data = new HashMap<>();
        String phoneMiddleInfo = noteCode.getPhoneNumbers().substring(3, 8);
        String phoneInfo = noteCode.getPhoneNumbers().replace(phoneMiddleInfo, "******");
        data.put("phoneInfo", phoneInfo);
        data.put("rediskey",token);
        System.out.println(data);
        return  new ResultData().setCode("0000").setMsg("短信验证码发送成功！").setData(data);
    }

    /**
     * 跳转到修改密码的页面
     * @return
     */
    @RequestMapping("/toUpdatePassword")
    public String toUpdatePassword(){
        return "updatepassword";
    }

    /**
     * 跳转到修改密码的页面--短信
     * @return
     */
    @RequestMapping("/toUpdatePasswordByNoteCode")
    public String toUpdatePasswordByNoteCode(String username,String newpassword,String code,String token){
        String noteCode = stringRedisTemplate.opsForValue().get(token);
        System.out.println(noteCode);
        if (noteCode != null && code.equals(noteCode)){
            User user = userService.queryByUserName(username);
            if (user != null){
                int result = userService.updatePasswordByUserName(username, newpassword);
                if (result > 0){
                    stringRedisTemplate.delete(token);
                    return "login";
                }
            }
        }

        return "forgetPasswordByNoteCode";
    }

    /**
     * 进行密码的修改--邮箱
     * @param token
     * @param newpassword
     * @return
     */
    @RequestMapping("/updatePassword")
    public String updatePasssword(String token,String newpassword){
        //从redis中获得token对应的用户名
        String username = stringRedisTemplate.opsForValue().get(token);
        if(username != null){
            //可以正常修改密码
            int result = userService.updatePasswordByUserName(username,newpassword);
            if(result > 0){
                //删除redis
                stringRedisTemplate.delete(token);
                return "login";
            }

        }
        return "updateError";
    }

    /**
     * 登陆账号
     * @return
     */
    @RequestMapping("/login")
    public String Login(String username, String password, String returnUrl, Model model, HttpServletResponse response){
        //进行登陆
        User user = userService.login(username,password);

        //如果没有调整的url
        if(returnUrl == null || returnUrl.equals("")){
            //默认跳转到首页
            returnUrl = "http://localhost:16666/";
        }

        if (user != null){
            //登陆成功，将用户信息写入redis
            String token = UUID.randomUUID().toString();
            redisTemplate.opsForValue().set(token,user);
            redisTemplate.expire(token,7,TimeUnit.DAYS);

            //将uuid写入cookie
            Cookie cookie = new Cookie("login_token",token);
            //设置cookie的最大存活时间，如果不设置，默认浏览器关闭就没有了，单位是秒
            cookie.setMaxAge(60 * 60 * 24 * 7);
            cookie.setPath("/");
            //将Cookie写入浏览器 -- respoonse
            response.addCookie(cookie);

            //登陆成功 - 重定向回首页
            try {
                return "redirect:http://localhost:16666/cart/merge?returnUrl=" + URLEncoder.encode(returnUrl, "utf-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            ;
        }
        //登陆失败
        return "login";
    }

    @RequestMapping("/isLogin")
    @ResponseBody
    public ResultData<User> isLogin(@CookieValue(value = "login_token",required = false)String loginToken){

        if(loginToken != null){
            User user = (User) redisTemplate.opsForValue().get(loginToken);
            if (user != null){
                //已经登陆
                return new ResultData<User>().setCode("0000").setMsg("已经登陆").setData(user);
            }
        }

        return new ResultData<User>().setCode("20001").setMsg("未登录");
    }

    /**
     *注销登陆
     * @param loginToken
     * @param response
     * @return
     */
    @RequestMapping("/logout")
    public String logout(@CookieValue(value = "login_token",required = false) String loginToken,HttpServletResponse response){

        if(loginToken != null) {
            //删除redis
            redisTemplate.delete(loginToken);
            //删除cookie
            Cookie cookie = new Cookie("login_token",loginToken);
            cookie.setMaxAge(0);//设置为0表示删除这条cookie
            cookie.setPath("/");
            response.addCookie(cookie);
        }

        //注销完成之后跳转至首页
        return "redirect:http://localhost:16666";
    }

}
