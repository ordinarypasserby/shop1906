package com.qf.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.qf.dao.CartMapper;
import com.qf.entity.Goods;
import com.qf.entity.Shopcart;
import com.qf.entity.User;
import com.qf.feign.GoodsFeign;
import com.qf.service.ICartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author DingYuHui
 * @Date 2019/10/22
 */
@Service
public class CartServiceImpl implements ICartService {

    @Autowired
    private CartMapper cartMapper;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private GoodsFeign goodsFeign;

    /**
     * 添加到购物车
     * @param cartToken
     * @param gid
     * @param gnumber
     * @param user
     * @param price
     * @return
     */
    @Override
    public String insertCart(String cartToken, Integer gid, Integer gnumber, User user, BigDecimal price) {

        Shopcart shopcart = new Shopcart(
                user != null ? user.getId() : null,
                gid,
                gnumber,
                price,
                null,
                null
                );

        //判断是否登陆
        if(user != null){
            //已经登陆
            //保存到数据库当中
            cartMapper.insert(shopcart);
        }else {
            //没有登陆
            //保存到redis
            cartToken = cartToken == null? UUID.randomUUID().toString() : cartToken;
            redisTemplate.opsForList().leftPush(cartToken,shopcart);
            redisTemplate.expire(cartToken,365, TimeUnit.DAYS);
            return cartToken;
        }


        return null;
    }

    /**
     * 查询购物车信息
     * @param cartToken
     * @param user
     * @return
     */
    @Override
    public List<Shopcart> queryShopCart(String cartToken, User user) {

        List<Shopcart> shopcarts = null;
        if (user != null){
            //已经登陆，从数据库查询购物车信息
            QueryWrapper queryWrapper = new QueryWrapper();
            queryWrapper.eq("uid",user.getId());
            queryWrapper.orderByDesc("create_time");
            shopcarts = cartMapper.selectList(queryWrapper);
        }else {
            //没有登陆
            if(cartToken != null){
                //获得临时购物车的长度
                Long size = redisTemplate.opsForList().size(cartToken);
                //获得所有购物车列表
                shopcarts = redisTemplate.opsForList().range(cartToken, 0, size);
            }
        }
        //遍历购物车集合，查询每个商品的详细信息
        if(shopcarts != null) {
            for (Shopcart shopcart : shopcarts) {
                //调用商品服务查询商品的信息
                Goods goods = goodsFeign.queryById(shopcart.getGid());
                shopcart.setGoods(goods);
            }
        }
        return shopcarts;
    }

    /**
     * 合并购物车
     * @param cartToken
     * @param user
     * @return
     */
    @Override
    public int mergeShopCart(String cartToken, User user) {
        if (cartToken != null){
            //获得临时购物车的长度
            Long size = redisTemplate.opsForList().size(cartToken);
            //获得所有购物车的列表
            List<Shopcart> shopcarts = redisTemplate.opsForList().range(cartToken, 0, size);

            //循环将临时购物车添加到永久购物车中
            for (Shopcart shopcart : shopcarts) {
                cartMapper.insert(shopcart);
            }

            //删除redis
            redisTemplate.delete(cartToken);
            return 1;

        }

        return 0;
    }


}
