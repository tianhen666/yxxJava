package com.lechi.yxx.controller;

import com.github.wxpay.sdk.WXPayConstants;
import com.github.wxpay.sdk.WXPayUtil;
import com.lechi.yxx.common.Configure;
import com.lechi.yxx.common.HttpRequest;
import com.lechi.yxx.common.OrderReturnInfo;
import com.lechi.yxx.common.RandomStringGenerator;
import com.lechi.yxx.service.WxCourseService;
import com.lechi.yxx.util.PayUtil;
import com.lechi.yxx.util.Result;
import com.thoughtworks.xstream.XStream;
import io.swagger.annotations.ApiOperation;
import jodd.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/wxpay")
public class WxPayController {

    @Autowired
    private WxCourseService wxCourseService;


/*    *//*private static final long serialVersionUID = 1L;
    private static final Logger L = Logger.getLogger(WxPayController.class);*//*

    *//*支付回调地址（你自己的请求地址，可以自己随意配置啦，写在这方便你理解）*//*
    private String notify_url = "https://xinhuo.21stf.org/mob/pay/wxpay/weixin/callback/wxNotify.do";

    //交易类型（这里是小程序）
    private final String trade_type = "JSAPI";

    //统一下单API接口链接（微信官方的接口）
    private final String url = "https://api.mch.weixin.qq.com/pay/unifiedorder";*/


    /**
     * 微信统一下单接口
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "微信统一下单接口")
    @RequestMapping(value = "/payment")
    public Result<String> payment(HttpServletRequest request, String money, String openid, String body){
        if (StringUtil.isEmpty(money)) {
            return new Result("500", "参数错误：money不能为空！", null);
        }
        if (StringUtil.isEmpty(openid)) {
            return  new Result("500", "参数错误：openid不能为空！", null);
        }
        if (StringUtil.isEmpty(body)) {
            return  new Result("500", "参数错误：body不能为空！", null);
        }
        Map<String,String> data = new HashMap<String,String>();
        Map<String,Object> map = new HashMap<String,Object>();
        //公众账号ID
        data.put("appid", Configure.getAppID());
        //商户号
        data.put("mch_id", Configure.getMch_id());
        //随机字符串
        data.put("nonce_str", RandomStringGenerator.getRandomStringByLength(32));
        //用户标识
        data.put("openid", openid);
        //商品描述
        data.put("body", body);
        //商户订单号
        data.put("out_trade_no", RandomStringGenerator.getRandomStringByLength(18));
        data.put("fee_type", "CNY");
        //标价金额
        data.put("total_fee", money);
        //终端IP
        data.put("spbill_create_ip", "127.0.0.1");
        //回调地址
        data.put("notify_url", "https://192.168.5.116:8089/wxpay/wxNotify");
        //交易类型
        data.put("trade_type", "JSAPI");
        //生成签名
        String signXml= "";
        try {
            //调用demo里面的sign生成方法
            signXml = WXPayUtil.generateSignedXml(data, Configure.getKey(), WXPayConstants.SignType.MD5);
            //url：统一下单API接口链接
            String result = HttpRequest.sendPost("https://api.mch.weixin.qq.com/pay/unifiedorder", signXml);
            System.out.println(result);
            XStream xStream = new XStream();
            XStream.setupDefaultSecurity(xStream);
            //出于安全考虑，这里必须限制类型，不然会报错
            xStream.allowTypes(new Class[]{OrderReturnInfo.class});
            xStream.alias("xml", OrderReturnInfo.class);

            OrderReturnInfo returnInfo = (OrderReturnInfo)xStream.fromXML(result);
            // 二次签名
            if ("SUCCESS".equals(returnInfo.getReturn_code()) && returnInfo.getReturn_code().equals(returnInfo.getResult_code())) {
                long time = System.currentTimeMillis()/1000;

                //生成签名（官方给出来的签名方法）
                Map<String,String> map2 = new HashMap<String,String>();
                map2.put("appId", Configure.getAppID());
                map2.put("timeStamp", String.valueOf(time));
                //这边的随机字符串必须是第一次生成sign时，微信返回的随机字符串，不然小程序支付时会报签名错误
                map2.put("nonceStr", returnInfo.getNonce_str());
                map2.put("package", "prepay_id=" + returnInfo.getPrepay_id());
                map2.put("signType", "MD5");

                String sign2 = WXPayUtil.generateSignature(map2, Configure.getKey(), WXPayConstants.SignType.MD5);
                System.out.println("二次签名的sign2----->"+sign2);
                //无效的签名方法
                //String sign1 = Signature.getSign(signInfo);
                Map<String,Object> payInfo = new HashMap<String,Object>();
                payInfo.put("timeStamp", String.valueOf(time));
                payInfo.put("nonceStr", returnInfo.getNonce_str());
                payInfo.put("prepay_id",returnInfo.getPrepay_id());
                payInfo.put("signType", "MD5");
                payInfo.put("paySign", sign2);
                map.put("status", 200);
                map.put("msg", "统一下单成功!");
                map.put("data", payInfo);

                // 此处可以写唤起支付前的业务逻辑

                // 业务逻辑结束
                return  new Result("200", "Success!", map);
            }
            map.put("status", 500);
            map.put("msg", "统一下单失败!");
            map.put("data", returnInfo);
            return  new Result("500", "统一下单失败!", map);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  new Result("200", "Success!", map);
    }

    /**
     * 微信小程序支付成功回调函数
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(value = "/wxNotify")
    public void wxNotify(HttpServletRequest request, HttpServletResponse response) throws Exception{
        BufferedReader br = new BufferedReader(new InputStreamReader((ServletInputStream)request.getInputStream()));
        String line = null;
        StringBuilder sb = new StringBuilder();
        while((line = br.readLine()) != null){
            sb.append(line);
        }
        br.close();
        //sb为微信返回的xml
        String notityXml = sb.toString();
        String resXml = "";
        System.out.println("接收到的报文：" + notityXml);

        @SuppressWarnings("unchecked")
        Map<String, String> map = WXPayUtil.xmlToMap(notityXml);

        String returnCode = (String) map.get("return_code");
        if("SUCCESS".equals(returnCode)){
            //验证签名是否正确
            //回调验签时需要去除sign和空值参数
            Map<String, String> validParams = PayUtil.paraFilter(map);
            //把数组所有元素，按照“参数=参数值”的模式用“&”字符拼接成字符串
            String validStr = PayUtil.createLinkString(validParams);
            //拼装生成服务器端验证的签名
            String sign = PayUtil.sign(validStr, Configure.getKey(), "utf-8").toUpperCase();
            // 因为微信回调会有八次之多,所以当第一次回调成功了,那么我们就不再执行逻辑了

            //根据微信官网的介绍，此处不仅对回调的参数进行验签，还需要对返回的金额与系统订单的金额进行比对等
            if(sign.equals(map.get("sign"))){
                /**此处添加自己的业务逻辑代码start**/
                // bla bla bla....
                /**此处添加自己的业务逻辑代码end**/
                //通知微信服务器已经支付成功
                resXml = "<xml>" + "<return_code><![CDATA[SUCCESS]]></return_code>"
                        + "<return_msg><![CDATA[OK]]></return_msg>" + "</xml> ";
            } else {
                System.out.println("微信支付回调失败!签名不一致");
            }
        }else{
            resXml = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>"
                    + "<return_msg><![CDATA[报文为空]]></return_msg>" + "</xml> ";
        }
        System.out.println(resXml);
        System.out.println("微信支付回调数据结束");

        BufferedOutputStream out = new BufferedOutputStream(
                response.getOutputStream());
        out.write(resXml.getBytes());
        out.flush();
        out.close();
    }





}
