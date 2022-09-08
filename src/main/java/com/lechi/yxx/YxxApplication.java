package com.lechi.yxx;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@MapperScan("com.lechi.yxx.mapper")
public class YxxApplication {

    public static void main(String[] args) {
        SpringApplication.run(YxxApplication.class, args);
    }

}
