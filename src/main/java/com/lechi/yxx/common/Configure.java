package com.lechi.yxx.common;

public class Configure {

    // 商户支付秘钥
    private static String key = "XXXXXXXXXXXXX";
    //小程序ID
    private static String appID = "wxc4b2eb2e4a97ff84";
    //商户号
    private static String mch_id = "1624505327";
    // 小程序的secret
    private static String secret = "a9b5107dd3b53081d7bf86168b35270d";

    public static String getKey() {
        return key;
    }
    public static void setKey(String key) {
        Configure.key = key;
    }
    public static String getAppID() {
        return appID;
    }
    public static void setAppID(String appID) {
        Configure.appID = appID;
    }
    public static String getMch_id() {
        return mch_id;
    }
    public static void setMch_id(String mch_id) {
        Configure.mch_id = mch_id;
    }
    public static String getSecret() {
        return secret;
    }
    public static void setSecret(String secret) {
        Configure.secret = secret;
    }


}
