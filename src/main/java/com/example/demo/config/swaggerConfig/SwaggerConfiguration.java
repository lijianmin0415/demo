package com.example.demo.config.swaggerConfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.ApiSelectorBuilder;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@ComponentScan(basePackages = "com.example.demo")
@EnableSwagger2
public class SwaggerConfiguration {


    @Autowired
    private SwaggerInfo swaggerInfo;

    @Bean
    public Docket controllerApi() {
        Docket docket = new Docket(DocumentationType.SWAGGER_2)
                .groupName(swaggerInfo.getGroupName())
                .apiInfo(apiInfo());
        ApiSelectorBuilder builder = docket.select();
        if (!StringUtils.isEmpty(swaggerInfo.getBasePackage())) {
            builder = builder.apis(RequestHandlerSelectors.basePackage(swaggerInfo.getBasePackage()));
        }
        if (!StringUtils.isEmpty(swaggerInfo.getAntPath())) {
            builder = builder.paths(PathSelectors.ant(swaggerInfo.getAntPath()));
        }
        return builder.build();

    }

    @Bean
    public Docket dufaultApi() {
        Docket docket = new Docket(DocumentationType.SWAGGER_2);
        ApiSelectorBuilder builder = docket.select();
        builder = builder.apis(RequestHandlerSelectors.basePackage("com.example.demo"));
        return builder.build();
    }


    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title(swaggerInfo.getTitle())
                .description(swaggerInfo.getDescription())
//                .termsOfServiceUrl("https://www.lbonline.cn/")
                .contact(new Contact("Mr.Li", null, "lijianmin0415@163.com or lijm@louddt.com"))
                .license(swaggerInfo.getLicense())
//                .licenseUrl("https://www.lbonline.cn/")
                .version("2.0")
                .build();
    }
}
