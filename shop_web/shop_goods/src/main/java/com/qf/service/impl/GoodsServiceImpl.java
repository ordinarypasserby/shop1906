package com.qf.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.qf.dao.GoodsImageMapper;
import com.qf.dao.GoodsMapper;
import com.qf.dao.GoodsSeckillMapper;
import com.qf.entity.Goods;
import com.qf.entity.GoodsImages;
import com.qf.entity.GoodsSeckill;
import com.qf.feign.ItemFeign;
import com.qf.feign.SearchFeign;
import com.qf.service.IGoodsService;
import com.qf.util.ContactUtil;
import com.qf.util.TimeUtil;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GoodsServiceImpl implements IGoodsService {

    @Autowired
    private GoodsMapper goodsMapper;

    @Autowired
    private GoodsImageMapper goodsImageMapper;

    @Autowired
    private GoodsSeckillMapper goodsSeckillMapper;

    @Autowired
    private SearchFeign searchFeign;

    @Autowired
    private ItemFeign itemFeign;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public List<Goods> queryAllGoods() {
        return goodsMapper.queryAllGoods();
    }

    @Override
    @Transactional
    @CacheEvict(cacheNames = "seckill",key = "'indexList'")
    public int insertGoods(Goods goods) {

        //保存商品信息
        goodsMapper.insert(goods);

        //保存商品图片

        //封装一个封面的对象
        GoodsImages fengMian = new GoodsImages(
                goods.getId(),
                null,
                goods.getFengmian(),
                1
        );

        goodsImageMapper.insert(fengMian);

        //保存其他图片
        for (String otherUrl : goods.getOtherImg()) {
            GoodsImages otherImage = new GoodsImages(
                    goods.getId(),
                    null,
                    otherUrl,
                    0
            );

            goodsImageMapper.insert(otherImage);
        }

//        //调用搜索服务将最新的商品信息保存到solr索引库中
//        if(!searchFeign.insertSolr(goods)){
//            //索引库添加失败
//            throw new RuntimeException("索引库添加失败！");
//        }
//
//        //调用详情服务生成该商品的静态页面
//        itemFeign.createHtml(goods);

        //添加秒杀商品
        if (goods.getType() == 2){
            GoodsSeckill goodsSeckill = goods.getGoodsSeckill();
            goodsSeckill.setGid(goods.getId());
            //保存秒杀信息
            goodsSeckillMapper.insert(goodsSeckill);

            //如果是秒杀商品，将保存到redis中

            //计算评分
            String profix = TimeUtil.dateSCore(goods.getGoodsSeckill().getStartTime());
            //将商品信息保存到redis
            stringRedisTemplate.opsForSet().add(ContactUtil.REDIS_SECKILL_START_SET + "_" + profix, goods.getId()+"");

            //将库存数保存到redis中作为判断标识
            Integer seckillSave = goods.getGoodsSeckill().getSeckillSave();
            stringRedisTemplate.opsForValue().set(ContactUtil.REDIS_SECKILL_SAVE+"_"+goods.getId(),seckillSave+"");
        }

        //将goods发送到指定的交换机中
        rabbitTemplate.convertAndSend("goods_exchange", goods.getType() == 1?"normal":"seckill", goods);

        return 1;
    }

    /**
     * 根据商品id查询商品详情
     * @param gid
     * @return
     */
    @Override
    public Goods queryById(Integer gid) {
        //查询商品的详细信息
        Goods goods = goodsMapper.selectById(gid);
        //查询商品的封面
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("gid",gid);
        queryWrapper.eq("isfengmian",1);
        GoodsImages goodsImages = goodsImageMapper.selectOne(queryWrapper);

        //设置封面
        goods.setFengmian(goodsImages.getUrl());

        QueryWrapper queryWrapper1 = new QueryWrapper();
        queryWrapper1.eq("gid",gid);
        GoodsSeckill goodsSeckill = goodsSeckillMapper.selectOne(queryWrapper1);
        goods.setGoodsSeckill(goodsSeckill);

        return goods;
    }

    /**
     * 查询当前场的秒杀信息列表
     * @return
     */
    @Override
    @Cacheable(cacheNames = "seckill", key = "'indexList'")
    public List<Map<String, Object>> querySeckillByTime() {

        System.out.println("查询了数据库中秒杀的商品信息");

        List<Map<String, Object>> seckillList = new ArrayList<>();
        //获得当前的秒杀信息
        List<Goods> nowSeckillGoodsList = new ArrayList<>();
        //查询当前时间段的秒杀商品
        List<GoodsSeckill> nowSeckill = goodsSeckillMapper.queryNow();
        if(nowSeckill != null) {
            for (GoodsSeckill goodsSeckill : nowSeckill) {
                //查询秒杀商品的信息
                Goods goods = queryById(goodsSeckill.getGid());

                goods.setGoodsSeckill(goodsSeckill);
                nowSeckillGoodsList.add(goods);
            }
        }
        Map<String,Object> nowMap = new HashMap<>();
        nowMap.put("startTime", TimeUtil.getNow());
        nowMap.put("endTime", TimeUtil.getNext(1));
        nowMap.put("goods",nowSeckillGoodsList);

        //获得下个时间段的秒杀信息
        List<Goods> nextSeckillGoodsList = new ArrayList<>();
        List<GoodsSeckill> nextSeckill = goodsSeckillMapper.queryNext();
        if (nextSeckill != null){
            for (GoodsSeckill goodsSeckill : nextSeckill) {
                Goods goods = queryById(goodsSeckill.getGid());
                goods.setGoodsSeckill(goodsSeckill);
                nextSeckillGoodsList.add(goods);
            }
        }

        Map<String,Object> nextMap = new HashMap<>();
        nextMap.put("startTime",TimeUtil.getNext(1));
        nextMap.put("endTime",TimeUtil.getNext(2));
        nextMap.put("goods",nextSeckillGoodsList);

        //将当前以及之后的秒杀商品信息加入集合
        seckillList.add(nowMap);
        seckillList.add(nextMap);
        System.out.println(seckillList);
        return seckillList;
    }

    /**
     * 减少库存
     * @param gid
     * @return
     */
    @Override
    public int reduceStocks(Integer gid) {

        return goodsSeckillMapper.reduceStocks(gid);
    }
}
