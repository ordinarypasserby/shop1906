package com.qf.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author DingYuHui
 * @Date 2019/10/9
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class Goods extends BaseEntity {

    private String subject;
    private String info;
    private BigDecimal price;
    private Integer save;
    private Integer type;

    //封面
    @TableField(exist = false)
    private String fengmian;

    //其他的图片
    @TableField(exist = false)
    private List<String> otherImg;

    //查询用来存放所有的图片的信息
    @TableField(exist = false)
    private List<GoodsImages> goodsImages;

    //商品对应的秒杀信息
    @TableField(exist = false)
    private GoodsSeckill goodsSeckill;

}
