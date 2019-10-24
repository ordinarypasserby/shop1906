package com.qf.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

/**
 * @author DingYuHui
 * @Date 2019/10/23
 * 订单详情实体类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain =true)
public class OrderDetils extends BaseEntity {

    private Integer oid;
    private Integer gid;
    private String subject;
    private BigDecimal price;
    private Integer number;
    private BigDecimal detilsPrice;

    @TableField(exist = false)
    private Goods goods;
}
