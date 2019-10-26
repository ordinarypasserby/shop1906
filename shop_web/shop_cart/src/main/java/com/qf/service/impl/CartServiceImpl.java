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
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Arrays;
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
     * @return
     */
    @Override
    public String insertCart(String cartToken, Integer gid, Integer gnumber, User user) {
        //根据id查出商品的信息
        Goods goods = goodsFeign.queryById(gid);
        //得出购物车商品小计
         BigDecimal subtotal = goods.getPrice().multiply(BigDecimal.valueOf(gnumber));


        Shopcart shopcart = new Shopcart(
                user != null ? user.getId() : null,
                gid,
                gnumber,
                goods.getPrice(),
                subtotal,
                null
                );

        //判断是否登陆
        if(user != null){
            //已经登陆
            //保存到数据库当中
            //判断购物车中是否已存在相同商品
            QueryWrapper queryWrapper = new QueryWrapper();
            queryWrapper.eq("uid",user.getId());
            List<Shopcart> list = cartMapper.selectList(queryWrapper);
            for (Shopcart shopcart1 : list) {
                //判断新添加的商品与购物车中的商品是否重复
                if(shopcart1.getGid() == gid){
                    //设置新添加的商品属性
                    shopcart.setNumber(shopcart1.getNumber()+gnumber);
                    shopcart.setSubtotal(shopcart1.getSubtotal().add(subtotal));
                    QueryWrapper queryWrapper1 = new QueryWrapper();
                    queryWrapper1.eq("gid",gid);
                    //修改数据库
                    cartMapper.update(shopcart,queryWrapper1);
                    return null;
                }
            }

            cartMapper.insert(shopcart);
        }else {
            //没有登陆
            //保存到redis
            //cartToken = cartToken == null? UUID.randomUUID().toString() : cartToken;

            //判断是否存在购物车
            if (cartToken == null || cartToken.equals("")){
                //不存在购物车，直接添加至redis
                cartToken = UUID.randomUUID().toString();
                redisTemplate.opsForList().leftPush(cartToken,shopcart);
                redisTemplate.expire(cartToken,365, TimeUnit.DAYS);
                return cartToken;
            }else {
                //已经存在购物车
                //获得临时购物车的长度
                Long size = redisTemplate.opsForList().size(cartToken);
                //获得所有购物车列表
                List<Shopcart> range = redisTemplate.opsForList().range(cartToken, 0, size);
                //删除原本的购物车
                redisTemplate.delete(cartToken);
                //遍历存下来的购物车列表
                for (Shopcart shopcart1 : range) {
                    //判断添加的商品是否与购物车有重复
                    if (shopcart1.getGid().equals(gid)){
                        //有相同商品，设置新添加的商品属性
                        shopcart.setNumber(shopcart1.getNumber()+gnumber);
                        shopcart.setSubtotal(shopcart1.getSubtotal().add(subtotal));
                    }else {
                        //将除新添加商品之外的商品列表存在一个新的购物车-redis
                        redisTemplate.opsForList().leftPush(cartToken,shopcart1);
                    }
                }
                //将新添加的商品添加到新的购物车-redis
                redisTemplate.opsForList().leftPush(cartToken,shopcart);
                return cartToken;
            }
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
    @Transactional
    @Override
    public int mergeShopCart(String cartToken, User user) {
        if (cartToken != null){
            //获得临时购物车的长度
            Long size = redisTemplate.opsForList().size(cartToken);
            //获得所有购物车的列表
            List<Shopcart> shopcarts = redisTemplate.opsForList().range(cartToken, 0, size);

            //获得当前用户的购物车列表
            QueryWrapper queryWrapper = new QueryWrapper();
            queryWrapper.eq("uid",user.getId());
            List<Shopcart> list = cartMapper.selectList(queryWrapper);
            //循环将临时购物车添加到永久购物车中
            for (Shopcart shopcart : shopcarts) {
                //判断临时购物车与用户购物车的商品是否有重复
                for (Shopcart usershopcart : list) {
                    if (usershopcart.getGid().equals(shopcart.getGid())){
                        System.out.println("jin");
                        QueryWrapper queryWrapper1 = new QueryWrapper();
                        queryWrapper.eq("uid",user.getId());
                        queryWrapper.eq("gid",usershopcart.getGid());
                        cartMapper.delete(queryWrapper1);
                    }
                }
                shopcart.setUid(user.getId());
                cartMapper.insert(shopcart);
            }

            //删除redis
            redisTemplate.delete(cartToken);
            return 1;

        }

        return 0;
    }

    /**
     * 根据商品id，用户id查询购物车信息
     * @param gid
     * @param uid
     * @return
     */
    @Override
    public List<Shopcart> queryByGid(Integer[] gid, Integer uid) {
        List<Shopcart> shopcarts = cartMapper.queryByGid(gid,uid);
        //根据购物车信息查询商品的详细信息
        if(shopcarts != null){
            for (Shopcart shopcart : shopcarts) {
                //调用商品服务查询商品的信息
                Goods goods = goodsFeign.queryById(shopcart.getGid());
                shopcart.setGoods(goods);
            }
        }
        return shopcarts;
    }

    /**
     * 根据购物车的id数组，查询购物车列表
     * @param ids
     * @return
     */
    @Override
    public List<Shopcart> queryByIds(Integer[] ids) {
        List<Shopcart> shopcarts = cartMapper.selectBatchIds(Arrays.asList(ids));
        //根据购物车信息查商品的详细信息
        if (shopcarts != null){
            for (Shopcart shopcart : shopcarts) {
                Goods goods = goodsFeign.queryById(shopcart.getGid());
                shopcart.setGoods(goods);
            }
        }
        return shopcarts;
    }

    /**
     * 根据购物车id列表删除购物车信息
     * @param ids
     * @return
     */
    @Override
    public int deleteByIds(Integer[] ids) {
        return cartMapper.deleteBatchIds(Arrays.asList(ids));
    }

    /**
     * 修改购物车中商品的数量
     * @param user
     * @param gid
     * @param number
     * @return
     */
    @Override
    public Shopcart updateGoodsNumber(User user, Integer gid, Integer number) {

        if (user ==null){

        }else {
            QueryWrapper queryWrapper = new QueryWrapper();
            queryWrapper.eq("gid",gid);
            queryWrapper.eq("uid",user.getId());
            Shopcart shopcart = cartMapper.selectOne(queryWrapper);
            shopcart.setGid(gid)
                    .setNumber(number)
                    .setUid(user.getId())
                    .setSubtotal( shopcart.getCartPrice().multiply(BigDecimal.valueOf(number)));
            cartMapper.updateById(shopcart);

            Shopcart shopcart1 = cartMapper.selectOne(queryWrapper);
            return shopcart1;
        }

        return null;
    }


}
