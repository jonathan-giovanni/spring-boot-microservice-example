package com.hv.example.microservice.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {


    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .components(new Components())
                .info(new Info().title("SPRING-BOOT-MICROSERVICE-EXAMPLE").description(
                        "This is a spring boot microservice example. doc using springdoc-openapi and OpenAPI 3.").version("1.0.0"));
    }


}
