package com.dental.apigateway.filter;

import com.dental.apigateway.model.ApiResponse;
import com.dental.apigateway.model.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {

    @Autowired
    private RouteValidator validator;

    @Autowired
    private WebClient.Builder webClientBuilder;

    public AuthenticationFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) -> {
            if (validator.isSecured.test(exchange.getRequest())) {
                if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                    return onError(exchange, HttpStatus.UNAUTHORIZED,"UNAUTHORIZED");
                }
                String authHeader = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
                if (authHeader != null && authHeader.startsWith("Bearer ")) {
                    authHeader = authHeader.substring(7);
                }
                ParameterizedTypeReference<ApiResponse<String>> responseType =
                    new ParameterizedTypeReference<>() {};
                try {
                    Token token = new Token();
                    token.setToken(authHeader);
                    return webClientBuilder.build().post()
                            .uri("http://enrollment-service/api/enrollment/auth/validate")
                            .bodyValue(token)
                            .retrieve()
                            .toEntity(responseType)
                            .flatMap(responseEntity -> {
                                System .out.println(responseEntity.getBody());
                                if (responseEntity.getBody().isError() == false) {
                                    return chain.filter(exchange);
                                } else {
                                    return onError(exchange,  HttpStatus.UNAUTHORIZED,responseEntity.getBody().getData());
                                }
                            }).onErrorResume(e -> onError(exchange,HttpStatus.UNAUTHORIZED,"401 Unauthorized from Auth Server"));
                } catch (Exception e) {
                    return onError(exchange, HttpStatus.BAD_REQUEST,e.getMessage());
                }
            }
            return chain.filter(exchange);
        });
    }

    private Mono<Void> onError(ServerWebExchange exchange, HttpStatus httpStatus,String error) {
        exchange.getResponse().setStatusCode(httpStatus);
        exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);
        String response = "{" +
                "\"status\":" + httpStatus.value() +
                ", \"error\":" + true +
                ", \"message\":\"" + error + '\"' +
                ", \"data\":\"" + error + '\"' +
                '}';

        return exchange.getResponse().writeWith(
                Mono.just(exchange.getResponse()
                        .bufferFactory()
                        .wrap(response.getBytes()))
        );
    }

    public static class Config {

    }
}
