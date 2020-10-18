package com.chainup.core.config;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.RequestParameterBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ParameterType;
import springfox.documentation.service.RequestParameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.ArrayList;
import java.util.List;

/**
 * https://www.cnblogs.com/ruiyeclub/p/13334826.html
 */
@Configuration
@Slf4j
public class Swagger3Config {

    @Bean
    public Docket buildDocket() {
        log.info("buildDocket ...");

        //request header 参数
//        List<RequestParameter> parameters = new ArrayList<>();
//        RequestParameterBuilder builder = new RequestParameterBuilder();
//        RequestParameter requestParameter = builder.required(false).in(ParameterType.HEADER).name("login-token").description("登录token").build();
//        parameters.add(requestParameter);

        return new Docket(DocumentationType.OAS_30)
                .host("http://localhost:8080")
                .pathMapping("/")
                .apiInfo(buildApiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.chainup.action"))
                .apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build();
                //.globalRequestParameters(parameters);
    }

    private ApiInfo buildApiInfo() {
        return new ApiInfoBuilder()
                .title("meeting服务端平台")
                .version("1.0.0")
                .build();
    }
}
