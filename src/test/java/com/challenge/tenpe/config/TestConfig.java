package com.challenge.tenpe.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

import jakarta.servlet.Filter;

@TestConfiguration
public class TestConfig {

    @Bean
    @Primary
    public Filter rateLimitFilter() {
        return (request, response, chain) -> chain.doFilter(request, response);
    }
}
