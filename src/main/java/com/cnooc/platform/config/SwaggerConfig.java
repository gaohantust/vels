package com.cnooc.platform.config;
/**
 * @ClassName SwaggerConfig.java
 * @author Tong
 * @version V1.0
 * @Description TODO
 * @createTime 2021年02月26日 14:43:00
 */

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @program: qhdApi
 * @description: Swagger2配置
 * @author: TONG
 * @create: 2021-02-26 14:43
 **/
@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .pathMapping("/")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.cnooc.maximo.security.ctrl"))
                .paths(PathSelectors.any())
                .build().apiInfo(new ApiInfoBuilder()
                        .title("Vels-API")
                        .build());
    }
}
