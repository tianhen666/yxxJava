package com.lechi.yxx.config;


import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Data
public class FtpConfig {

    @Value("${ftpServer.ftp_ip}")
    String ip;
    @Value("${ftpServer.ftp_port}")
    int port;
    @Value("${ftpServer.ftp_username}")
    String username;
    @Value("${ftpServer.ftp_password}")
    String password;
    @Value("${ftpServer.ftp_uploadPath}")
    String uploadPath;
    /*@Value("${ftpServer.downPath}")
    String downPath;*/

    //在Controller中用于第二层与第三层路径的拼接
    public  static String store(){
        return  "/store";
    }

    public  static String activity(){
        return  "/activity";
    }

    public  static String getcase(){
        return  "/case";
    }

    public  static String doctor(){
        return  "/doctor";
    }

    public  static String product(){
        return  "/product";
    }


}
