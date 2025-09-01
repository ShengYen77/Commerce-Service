package com.gtelant.commerce.service.configs;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringDocConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("E-Commerce Admin Dashboard API")
                        .version("1.0.0")
                        .description("這是一個基於 Spring Boot + MySQL 的後台管理系統 API 文件")
                        .contact(new Contact()
                                .name("ShengYen77")
                                .email("test@gmail.com")
                                .url("https://github.com/ShengYen77"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("http://springdoc.org")));
    }
}
