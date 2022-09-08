package com.lechi.yxx.controller;

import com.github.wxpay.sdk.WXPay;
import com.lechi.yxx.config.MyPayConfig;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

import static com.lechi.yxx.util.JwtUtil.parseJWT;

@RestController
@RequestMapping("/pay")
public class PaymentController {

    @RequestMapping("/payment")
    public Object getpayment(HttpServletRequest request,String token,String productId) throws Exception {
//	获取到当前登录用户，因为这里我保存了openid , 方法大家可以自己处理，这里就不展示了
        String openid = parseJWT(token);
        //当前就是我们自己配置的支付配置。appid 商户号 key 什么的；
        MyPayConfig config = new MyPayConfig();
        //当前类是官方为我们封装的一些使用的方法
        WXPay wxpay = new WXPay(config);
        //获取到 IP
        String clientIp = getIpAddress(request);
        System.err.println(clientIp);
        //封装请求参数 参数说明看API文档，当前就不进行讲解了
        Map<String, String> data = new HashMap<String, String>();
        data.put("body", "微信支付");
        data.put("out_trade_no", "2016090910595900000012");
        //此处设备或商品编号
        data.put("device_info", "12345679");
        // 货币类型  人民币
        data.put("fee_type", "CNY");

        // 支付中没有小数点，起步以分做为单们，当前为1 分钱，所以自行调整金额 ，这里可以做为传参，
        //选取商品金额传到后端来
        data.put("total_fee", "1");

        data.put("spbill_create_ip", "123.12.12.123");
        data.put("notify_url", "http://www.example.com/wxpay/notify");
        // 此处指定JSAPI
        data.put("trade_type", "JSAPI");
        data.put("product_id", productId);
        data.put("openid", openid);
        //调用统一下单方法
        Map<String, String> order = wxpay.unifiedOrder(data);
        //获取到需要的参数返回小程序
        return order;

    }

    // 获取 IP
    public static String getIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

}
