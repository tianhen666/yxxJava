package com.lechi.yxx.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Calendar;
import java.util.Date;

public class JwtUtil {

    //设置过期时间
    private static final int EXPIRE_TIME =1;
    //token秘钥
    private static final String TOKEN_SECRET = "f26e587c28064d0e855e72c0a6a0e618";

    public static String sign(String openid) {
        Calendar nowTime = Calendar.getInstance();
        //过期时间
        nowTime.add(Calendar.HOUR_OF_DAY, EXPIRE_TIME);
        Date expireDate = nowTime.getTime();
        String token = JWT.create()
                //这是在设置第二部分信息，不要设置密码之类的，因为这些信息可以通过浏览器获取
                //openid
                .withClaim("openid",openid)
                //创建token的时间
                //签名时间
                .withIssuedAt(new Date())
                //设置token的过期时间
                //过期时间
                .withExpiresAt(expireDate)
                //设置第一部分
                //签名
                .sign(Algorithm.HMAC256(TOKEN_SECRET));
        return token;
        }


    public static DecodedJWT verify(String token){
        //如果有任何验证异常，此处都会抛出异常 我们需要在拦截器调用这个方法，捕获异常，然后返回错误信息给前端
            DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC256(TOKEN_SECRET)).build().verify(token);
            return decodedJWT;
    }

    /**
     * 获取token中的 payload 也就是第二部分的信息
     */
    public static DecodedJWT getTokenInfo(String token) {
        DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC256(TOKEN_SECRET)).build().verify(token);
        //使用 TokenUtils.getTokenInfo(token).getClaim("account").asString()
        return decodedJWT;
    }


    public static String parseJWT(String token){
        /**
         * @desc   解密token，返回一个map
         * @params [token]需要校验的串
         **/
        DecodedJWT decodeToken = JWT.decode(token);
        return decodeToken.getClaim("openid").asString();
    }
    public static boolean isJwtExpired(String token){
        /**
         * @desc 判断token是否过期
         * @author lj
         */
        try {
            DecodedJWT decodeToken = JWT.decode(token);
            return decodeToken.getExpiresAt().before(new Date());
        } catch(Exception e){
            return true;
        }
    }





}
