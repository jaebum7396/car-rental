package com.rental.common.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
        .info(
            new Info()
            .title("Car Rental API")
            .description("자동차 대여 시스템 API 문서")
            .version("1.0.0")
            .contact(
                new Contact()
                .name("주재범")
                .email("jaebum7396@naver.com")
            )
        )
        .servers(
            List.of(
                new Server()
                .url("http://localhost:8080")
                .description("Local Server")
            )
        );
    }
}