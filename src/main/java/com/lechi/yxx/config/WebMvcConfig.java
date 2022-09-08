package com.lechi.yxx.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        List<String> excludePathLists= new ArrayList<>();
        excludePathLists.add("/wx/login");
        excludePathLists.add("/wxpay/payment");
        registry.addInterceptor(new TokenConfig()).addPathPatterns("/**").excludePathPatterns(excludePathLists);
        }
}



