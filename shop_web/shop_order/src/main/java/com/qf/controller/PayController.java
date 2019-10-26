package com.qf.controller;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.qf.entity.Orders;
import com.qf.service.IOrderService;
import com.qf.util.AliPayUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author DingYuHui
 * @Date 2019/10/26
 */
@Controller
@RequestMapping("/pay")
public class PayController {

    @Autowired
    private IOrderService oderService;

    /**
     * 调用支付宝进行支付
     */
    @RequestMapping("/alipay")
    public void aliPay(Integer oid, HttpServletResponse response){

        //根据oid查询订单对象
        Orders orders = oderService.queryById(oid);
        System.out.println("需要下单的订单信息：" + orders);

        //获得支付宝核心对象
        AlipayClient alipayClient = AliPayUtil.getAlipayClient();

        //调用支付接口进行支付
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();//创建API对应的request
        alipayRequest.setReturnUrl("http://www.baidu.com");
        alipayRequest.setNotifyUrl("http://www.baidu.com");//在公共参数中设置回跳和通知地址
        alipayRequest.setBizContent("{" +
                "    \"out_trade_no\":\"" + orders.getOrderid() + "\"," +
                "    \"product_code\":\"FAST_INSTANT_TRADE_PAY\"," +
                "    \"total_amount\":" + orders.getAllprice().doubleValue() + "," +
                "    \"subject\":\"" + orders.getOrderid() + "\"," +
                "    \"body\":\"" + orders.getOrderid() + "\"," +
                "    \"extend_params\":{" +
                "    \"sys_service_provider_id\":\"2088511833207846\"" +
                "    }"+
                "  }");//填充业务参数
        String form="";
        try {
            form = alipayClient.pageExecute(alipayRequest).getBody(); //调用SDK生成表单
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        response.setContentType("text/html;charset=UTF-8");
        try {
            response.getWriter().write(form);//直接将完整的表单html输出到页面
            response.getWriter().flush();
            response.getWriter().close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
