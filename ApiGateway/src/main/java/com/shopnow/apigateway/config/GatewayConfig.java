package com.shopnow.apigateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

    @Bean
    public RouteLocator routerLocator(RouteLocatorBuilder builder){
        return builder
                .routes()
                .route(route -> route
                        .path("user-service/api/v1/users/**")
                        .uri("lb://user-service"))
                .build();
    }
}
