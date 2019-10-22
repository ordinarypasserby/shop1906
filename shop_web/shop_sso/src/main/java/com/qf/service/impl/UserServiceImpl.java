package com.qf.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.qf.dao.UserMapper;
import com.qf.entity.User;
import com.qf.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author DingYuHui
 * @Date 2019/10/18
 */
@Service
public class UserServiceImpl implements IUserService {
    @Autowired
    private UserMapper userMapper;

    /**
     * 用户注册
     * @param user
     * @return
     */
    @Override
    public int register(User user) {
        //判断用户名是否唯一
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("username",user.getUsername());
        Integer count = userMapper.selectCount(queryWrapper);

        if (count > 0){
            //用户已经存在
            return -1;
        }

        return userMapper.insert(user);
    }

    /**
     * 根据username查询用户信息
     * @param username
     * @return
     */
    @Override
    public User queryByUserName(String username) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("username",username);
        User user = userMapper.selectOne(queryWrapper);
        return user;
    }

    /**
     * 修改密码
     * @param username
     * @param newpassword
     * @return
     */
    @Override
    public int updatePasswordByUserName(String username, String newpassword) {
        User user = this.queryByUserName(username);
        user.setPassword(newpassword);
        //修改数据库
        return userMapper.updateById(user);
    }

    /**
     * 登陆
     * @param username
     * @param password
     * @return
     */
    @Override
    public User login(String username, String password) {
        User user = this.queryByUserName(username);
        if(user!=null && user.getPassword().equals(password)){
            //登陆成功
            return user;
        }
        return null;
    }
}
