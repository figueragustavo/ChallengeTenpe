package com.challenge.tenpe.config;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class SwaggerConfig {

    @Bean
    public GroupedOpenApi api() {
        return GroupedOpenApi.builder().group("ChallengeTenpe")
                .packagesToScan("com.challenge.tenpe.controller.impl")
                .pathsToMatch("/**")
                .build();
    }

    @Bean
    public OpenAPI springShopOpenApi() {
        return new OpenAPI().info(new Info().title("Challenge Tenpe")
                .description("Api de suma de 2 numeros agregando un porcentaje")
                .version("1.0.0")
                .contact(new Contact().name("Gustavo Figuera")
                        .url("www.example.com")
                        .email("figueragustavo@gmail.com")
                ));

    }
}
