package com.qf.util;

import com.qf.entity.Shopcart;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author DingYuHui
 * @Date 2019/10/23
 */
public class PriceUtil {

    /**
     * 计算购物车总额
     * @param shopcarts
     * @return
     */
    public static double allprice(List<Shopcart> shopcarts){

        BigDecimal allprice = BigDecimal.valueOf(0);
        //判断购物车是否为空
        if (shopcarts != null){
            for (Shopcart shopcart : shopcarts) {
//                //得到商品单价
//                BigDecimal pirce = shopcart.getGoods().getPrice();
//
//                //购买数量
//                BigDecimal number = BigDecimal.valueOf(shopcart.getNumber());
//
//                //相乘
//                BigDecimal multiply = pirce.multiply(number);

                //每个商品的小计
                BigDecimal subtotal = shopcart.getSubtotal();

                //累加到总价格中
                allprice = allprice.add(subtotal);
            }
        }
        return allprice.doubleValue();
    }
}
