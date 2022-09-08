package com.lechi.yxx.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableSwagger2
//@Profile("swagger")
public class SwaggerConfig {

    @Bean
    public Docket createRestApi() {
        // DocumentationType.SWAGGER_2 固定的，代表swagger2
        return new Docket(DocumentationType.SWAGGER_2)
                // 如果配置多个文档的时候，那么需要配置groupName来分组标识
//                .groupName("分布式任务系统")
                // 用于生成API信息
                .apiInfo(apiInfo())
                .select() // select()函数返回一个ApiSelectorBuilder实例,用来控制接口被swagger做成文档
                // 用于指定扫描哪个包下的接口
                .apis(RequestHandlerSelectors.basePackage("com.lechi.yxx.controller"))
                // 选择所有的API,如果你想只为部分API生成文档，可以配置这里
                .paths(PathSelectors.any())
                .build();
    }

    /**
     * 用于定义API主界面的信息，比如可以声明所有的API的总标题、描述、版本
     * @return
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                //  可以用来自定义API的主标题
                .title("乐齿项目API")
                // 可以用来描述整体的API
                .description("乐齿项目SwaggerAPI管理")
                // 用于定义服务的域名
                .termsOfServiceUrl("http://doc.html")
                // 可以用来定义版本。
                .version("1.0")
                .build();
    }
}