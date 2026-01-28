package com.sparta.order_management_service.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAI() {
        return new OpenAPI().info(
                new Info()
                        .title("Order Management Service API")
                        .description("주문 관리 서비스 REST API 문서")
                        .version("v1.0.0")
        );
    }
}
