package com.trader.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.ApiKeyVehicle;
import springfox.documentation.swagger.web.SecurityConfiguration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Arrays;

@Profile({"dev","stag"})
@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket productApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.trader.controller"))
                .paths(PathSelectors.any())
                .build().apiInfo(getApiInfo())
                .securitySchemes(Arrays.asList(apiKey()));
    }

    private ApiKey apiKey() {
        return new ApiKey("apiKey", "Authorization", "header");
    }

    @Bean
    public SecurityConfiguration security() {
        return new SecurityConfiguration(null, null, null, "my-api", "Bearer", ApiKeyVehicle.HEADER, "Authorization", ",");
        //return new SecurityConfiguration(null, null, "Customer API Realm", "Customer API", null, ApiKeyVehicle.HEADER, "Authorization", null);
    }

    private ApiInfo getApiInfo() {
        ApiInfo apiInfo = new ApiInfo(
                "Trader Arc Rest Apis",
                "Trader Arc - online app for trading coins",
                "2.0",
                "Terms of service",
                 new Contact("Cdacian & Co.","www.cdacian.com","cdacioan@gmail.com"),
                "Apache License Version 2.0",
                "https://www.apache.org/licenses/LICENSE-2.0");
        return apiInfo;
    }
}
