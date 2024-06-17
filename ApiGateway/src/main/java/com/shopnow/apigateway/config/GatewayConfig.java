package com.shopnow.apigateway.config;

import com.shopnow.apigateway.common.filters.AuthFilter;
import lombok.AllArgsConstructor;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@AllArgsConstructor
public class GatewayConfig {

    private final AuthFilter authFilter;

    @Bean
    public RouteLocator routerLocator(RouteLocatorBuilder builder) {
        return builder
                .routes()
                .route("user-service", route -> route
                        .path("/api/v1/users/**")
                        .filters(filter -> filter.filter(authFilter)
                                .stripPrefix(1))
                        .uri("lb://user-service"))
                .route("product-service", route -> route
                        .path("/api/v1/products/**")
                        .filters(filter -> filter.filter(authFilter)
                                .stripPrefix(1))
                        .uri("lb://product-service"))
                .route("order-service", route -> route
                        .path("/api/v1/orders/**")
                        .filters(filter -> filter.filter(authFilter)
                                .stripPrefix(1))
                        .uri("lb://order-service"))
                .build();
    }
}
