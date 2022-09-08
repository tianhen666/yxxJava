package com.lechi.yxx.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.api.R;
import com.lechi.yxx.model.User;
import com.lechi.yxx.service.IUserService;
import com.lechi.yxx.util.HttpRequest;
import com.lechi.yxx.util.OpenPlatformUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.lechi.yxx.util.JwtUtil.parseJWT;
import static com.lechi.yxx.util.JwtUtil.sign;


@Slf4j
@Api(tags = "登录接口")
@RestController
@RequestMapping("/wx")
public class WXController {

    @Value("${weixin.appid}")
    private String appid;

    @Value("${weixin.secret}")
    private String secret;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private IUserService userService;

    private OpenPlatformUtil openPlatformUtil;


    @ApiOperation("获取code")
    @GetMapping("/getcode")
    public String Getcode() throws Exception{
        // TODO 使用真实AK
        String ak = "394D9D85E33C1A3054AFA7F3BBB8A6E7";
        // TODO 使用真实SK
        String sk = "90712A427A2E9A5868886CBA43705F9F";
        // TODO 使用真实接口method
        String httpMethod = "GET";
        // TODO 使用真实接口uri，记住前后必须加‘/’
        String uri = "/api/authorized/code/";
        String queryString = "app_id="+appid+"&uid="+1;
        JSONObject jsonBody = new JSONObject();
        // TODO 使用真实接口入参
        Long timestamp = System.currentTimeMillis() / 1000;

        String requestRaw = StringUtils.join(new String[]{httpMethod, "@", uri, "@", queryString, "@", timestamp.toString()}, "");
        String signature = getHMAC(requestRaw, sk);
        CloseableHttpClient client = HttpClientBuilder.create().build();
        // TODO 使用真实接口地址
        HttpGet httpPost = new HttpGet("https://open-api.gaoding.com/api/authorized/code?"+"app_id="+appid+"&uid="+1);
        CloseableHttpResponse response = null;

        try {
            httpPost.addHeader("Content-Type", "application/json");
            httpPost.addHeader("X-Timestamp", timestamp.toString());
            httpPost.addHeader("X-AccessKey", ak);
            httpPost.addHeader("X-Signature", signature);
            response = client.execute(httpPost);
            HttpEntity responseEntity = response.getEntity();
            if (responseEntity != null) {
                String str ="";
                str = EntityUtils.toString(responseEntity);
                return str;
            }
        } finally {
            try {
                // 释放资源hl9876555
                if (client != null) {
                    client.close();
                }
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static String getHMAC(String data, String key) throws Exception {
        String HMAC_SHA1_ALGORITHM = "HmacSHA1";
        SecretKeySpec signinKey = new SecretKeySpec(key.getBytes(), HMAC_SHA1_ALGORITHM);
        Mac mac = Mac.getInstance(HMAC_SHA1_ALGORITHM);
        mac.init(signinKey);
        byte[] rawHmac = mac.doFinal(data.getBytes());
        return new String(Base64.encodeBase64(rawHmac));
    }



    @ApiOperation(value = "微信开放平台：授权事件接收URL,验证票据")
    @PostMapping("/pushTicket")
    public String wechatPlatformEvent(@RequestParam("timestamp") String timestamp,
                                      @RequestParam("nonce") String nonce,
                                      @RequestParam("msg_signature") String msgSignature,
                                      @RequestBody String postData) {
        return openPlatformUtil.parseRequest(timestamp, nonce, msgSignature, postData);
    }

    @ApiOperation(value = "用户微信登录授权")
    @RequestMapping("/login")
    public R wxLogin(@RequestParam("code") String code){
        Map<String,Object> map = new HashMap<>();
        String url = "https://api.weixin.qq.com/sns/jscode2session?" +
                "appid=" + appid +
                "&secret=" + secret +
                "&js_code=" + code +
                "&grant_type=authorization_code";
        //获取返回的数据 并将数据转换成json对象
        String jsonData = restTemplate.getForObject(url, String.class);
        JSONObject jsonObject = JSONObject.parseObject(jsonData);
        if (StringUtils.contains(jsonData, "errcode")) {
            //出错了
            return R.failed("出错");
        }
        String openid = jsonObject.getString("openid");
        String sessionKey = jsonObject.getString("session_key");
        String unionid = jsonObject.getString("unionid");
        map.put("openid",openid);
        map.put("session_key",sessionKey);
        map.put("unionid",unionid);

        String token = sign(openid);
        map.put("token",token);

        User one = userService.lambdaQuery().eq(User::getOpenid, openid).one();
        if (one==null){
            User user = new User();
            user.setOpenid(openid);
            user.setUnionid(unionid);
            userService.save(user);
        }
        return R.ok(map);
    }

    //获取手机号接口
    @ApiOperation(value = "用户手机号")
    @GetMapping("/mobile")
    public R mobile(@RequestParam("code") String code, HttpServletRequest request) {

        // http 的 header 中获得token
        String token = request.getHeader("authorization");

        // 获取token
        // 小程序唯一标识 (在微信小程序管理后台获取)
        //String appid = "";
        // 小程序的 app secret (在微信小程序管理后台获取)
        //String secret = "";
        // 授权（必填）
        String grant_type = "client_credential";
        //向微信服务器 使用登录凭证 code 获取 session_key 和 openid
        // 请求参数
        String params2 = "appid=" + appid + "&secret=" + secret + "&grant_type=" + grant_type;
        // 发送请求
        String sr2 = HttpRequest.sendGet("https://api.weixin.qq.com/cgi-bin/token", params2);
        // 解析相应内容（转换成json对象）
        JSONObject json2 = JSONObject.parseObject(sr2);
        String access_token = json2.getString("access_token");
        //使用获取到的token和接受到的code像微信获取手机号
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code",code);
        String url = ("https://api.weixin.qq.com/wxa/business/getuserphonenumber?access_token="+access_token);
        String sr3 = HttpRequest.sendPost(url,jsonObject);

        String openid = parseJWT(token);
        User one = userService.lambdaQuery().eq(User::getOpenid, openid).one();
        one.setMobile(sr3);
        userService.updateById(one);

        return R.ok(sr3);
    }


}

