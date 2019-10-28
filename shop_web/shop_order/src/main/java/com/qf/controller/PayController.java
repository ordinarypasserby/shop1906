package com.qf.controller;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.qf.entity.Orders;
import com.qf.service.IOrderService;
import com.qf.util.AliPayUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

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
        alipayRequest.setReturnUrl("http://localhost:16666/order/list");
        alipayRequest.setNotifyUrl("http://j27x460629.wicp.vip/pay/payCallback");//在公共参数中设置回跳和通知地址
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

    /**
     * 接收支付宝支付结果的异步回调方法
     */
    @RequestMapping("/payCallback")
    @ResponseBody
    public String payCallback(String charset,
                             String out_trade_no,
                             String trade_status,
                             String sign_type, HttpServletRequest request){

        Map<String,String> map = new HashMap<>();
        Map<String, String[]> parameterMap = request.getParameterMap();
        for(Map.Entry<String, String[]> entry : parameterMap.entrySet()){
            System.out.println(entry.getKey() + "=" + Arrays.toString(entry.getValue()));

            map.put(entry.getKey(), entry.getValue()[0]);
        }
        try {
            boolean flag = AlipaySignature.rsaCheckV1(
                                map,
                                "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAlLpAresGnNFqplpH1v4EL6mzVdXdjxNup+cFpnodQ0cTTAhU/T5MwOqjLft5EX63lszcpbRpp9CZOfoWAqtYFkB+vwXnzWACnbg908Xxbw+kF17H7JcZ+kDQF9k22kLQkCE1g8gVAePWGtVkpxg5EDlRGwSHSdTfhSBQxpglvEJmZeDFQJLfAaZ4cQkl52H6fozxXUvYT6rvtMllSDVthzus54iHp1YR31uRGUKhkLBqVChpNTBpKRb5sT+OcvBeoOcxnY1vLQ55wLxhQHN9yVnLCPd5TVyKmpVmD1/8HZgfUIsIt5ooPSdJeGH/WSd6hY89DEzuSynXZFUojuaZbwIDAQAB",
                                charset,
                                sign_type
                            );
            if (flag){
                //首次验签通过

                //接收异步通知，修改订单状态
                if(trade_status.equals("TRADE_SUCCESS") || trade_status.equals("TRADE_FINISHED")){
                    //支付成功
                    //根据订单号修改订单状态
                    oderService.updateOrderState(out_trade_no, 1);

                    return "success";
                }

            }else {
                //记录日志
                return "failure";
            }
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }

        return "failure";
    }
}
