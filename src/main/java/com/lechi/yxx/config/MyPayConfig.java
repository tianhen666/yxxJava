package com.lechi.yxx.config;

import com.github.wxpay.sdk.WXPayConfig;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

//  需要实现一下支付基本的配置，方便调用
public class MyPayConfig implements WXPayConfig{

    private byte[] certData;

    public void MyConfig() throws Exception {
        //此处暂时用不到，这里是读取证书的地方
    }

    @Override
    public String getAppID() {
        return "这里是你的appid";
    }

    @Override
    public String getMchID() {
        //申请普通商户时分配给你的商户号
        return "这里是你的商户号";
    }

    @Override
    public String getKey() {
        //这里的key 就是你在支付平台设置的API密钥
        return "这是就是你的Key了";
    }

    @Override
    public InputStream getCertStream() {
        ByteArrayInputStream certBis = new ByteArrayInputStream(this.certData);
        return certBis;
    }

    @Override
    public int getHttpConnectTimeoutMs() {
        return 8000;
    }

    @Override
    public int getHttpReadTimeoutMs() {
        return 10000;
    }
}

