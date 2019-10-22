package com.qf.service;

import com.qf.entity.User;

/**
 * @author DingYuHui
 * @Date 2019/10/18
 */
public interface IUserService {
    //注册用户
    int register(User user);

    //根据username查询用户信息
    User queryByUserName(String username);

    //修改密码
    int updatePasswordByUserName(String username, String newpassword);

    //登陆
    User login(String username, String password);
}
