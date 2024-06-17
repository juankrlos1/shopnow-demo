package com.shopnow.apigateway.common.filters;

import com.shopnow.apigateway.dto.TokenDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class AuthFilter implements GatewayFilter {

    private final WebClient webClient;

    private static final String AUTH_VALIDATE_URI = "http://localhost:8081/api/v1/users/validate";
    private static final String ACCESS_TOKEN_HEADER_NAME = "Authorization";

    public AuthFilter() {
        this.webClient = WebClient.builder().build();
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String path = exchange.getRequest().getURI().getPath();
        log.info("path: {}", path);
        if (path.equals("/api/v1/users/login") || path.equals("/api/v1/users/register")) {
            return chain.filter(exchange);
        }

        if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
            return this.onError(exchange, "Authorization header is missing");
        }

        final var tokenHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        assert tokenHeader != null;
        final var chunks = tokenHeader.split(" ");

        if (chunks.length != 2 || !chunks[0].equals("Bearer")) {
            return this.onError(exchange, "Invalid Authorization header format");
        }

        final var token = chunks[1];

        return this.webClient
                .post()
                .uri(AUTH_VALIDATE_URI)
                .header(ACCESS_TOKEN_HEADER_NAME, token)
                .retrieve()
                .bodyToMono(TokenDTO.class)
                .flatMap(response -> chain.filter(exchange))
                .onErrorResume(e -> this.onError(exchange, "Invalid token"));
    }

    private Mono<Void> onError(ServerWebExchange exchange, String error) {
        final var response = exchange.getResponse();
        response.setStatusCode(HttpStatus.FORBIDDEN);
        response.getHeaders().add("error-message", error);
        return response.setComplete();
    }
}
