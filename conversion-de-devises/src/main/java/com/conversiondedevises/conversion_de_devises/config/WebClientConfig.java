package com.conversiondedevises.conversion_de_devises.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {
    // ... other beans

    @Bean
    public WebClient.Builder webClientBuilder() {
        return WebClient.builder();
    }
}
