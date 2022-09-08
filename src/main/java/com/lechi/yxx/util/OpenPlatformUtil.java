package com.lechi.yxx.util;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.lechi.yxx.mapper.StoreWxappMapper;
import com.lechi.yxx.model.StoreWxapp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Map;
import java.util.concurrent.TimeUnit;

@Slf4j
public class OpenPlatformUtil {

    @Value("${weixin.appid}")
    private String APPID;

    @Value("${weixin.token}")
    private String Token;

    @Value("${weixin.key}")
    private String Key;

    @Value("${weixin.secret}")
    private String AppSecret;



    private RedisTemplate<String,Object> redisUtils;

    @Autowired
    private StoreWxappMapper storeWxappMapper;

    public String parseRequest(String timeStamp, String nonce, String msgSignature, String postData) {
        try {
            if (redisUtils.hasKey("ticket")) {
                return "success";
            }
            //这个类是微信官网提供的解密类,需要用到消息校验Token 消息加密Key和服务平台appid
            WXBizMsgCrypt pc = new WXBizMsgCrypt(Token, Key, APPID);
            String xml = pc.decryptMsg(msgSignature, timeStamp, nonce, postData);
            // 将xml转为map
            Map<String, String> result = WXXmlToMapUtil.xmlToMap(xml);
            String componentVerifyTicket = result.get("ComponentVerifyTicket");
            if (StringUtils.isNotEmpty(componentVerifyTicket)) {
                // 存储平台授权票据,保存ticket
                StoreWxapp storeWxapp = new StoreWxapp();
                if (storeWxappMapper.selectByAppId(APPID) == 0) {
                    storeWxapp.setAppId(APPID);
                    storeWxapp.setAppSecret(AppSecret);
                    storeWxapp.setPlatformKey(Key);
                    storeWxapp.setToken(Token);
                    storeWxapp.setComponentVerifyTicket(componentVerifyTicket);
                    storeWxappMapper.insert(storeWxapp);
                } else {
                    storeWxapp.setComponentVerifyTicket(componentVerifyTicket);
                    LambdaQueryWrapper<StoreWxapp> updateWrapper = new LambdaQueryWrapper<>();
                    updateWrapper.eq(StoreWxapp::getAppId, APPID);
                    storeWxappMapper.update(storeWxapp, updateWrapper);
                }
                redisUtils.opsForValue().set("ticket",componentVerifyTicket, 60 * 60 * 12, TimeUnit.SECONDS);
                log.info("微信开放平台，第三方平台获取【验证票据】成功");
            } else {
                log.error("微信开放平台，第三方平台获取【验证票据】失败");
            }
        } catch (AesException e) {
            log.error("微信开放平台，第三方平台获取【验证票据】失败,异常信息：" + e.getMessage());
        }
        return "success";
    }



}
