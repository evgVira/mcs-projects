package org.mcs.apigateway.security;

import lombok.extern.slf4j.Slf4j;
import org.mcs.apigateway.dto.AccessToken;
import org.mcs.apigateway.dto.RefreshToken;
import org.mcs.apigateway.token.model.deserializer.AccessTokenDeserializer;
import org.mcs.apigateway.token.model.deserializer.RefreshTokenDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class JwtAuthenticationFilter extends AbstractGatewayFilterFactory<JwtAuthenticationFilter.Config> {

    @Autowired
    private RouteValidator routeValidator;

    @Autowired
    private AccessTokenDeserializer accessTokenDeserializer;

    @Autowired
    private RefreshTokenDeserializer refreshTokenDeserializer;


    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) -> {
            if(routeValidator.isSecured.test(exchange.getRequest())){
                String header = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
                if(header != null && header.startsWith("Bearer ")){
                    String requestToken = header.split("Bearer ")[1];
                    AccessToken accessToken = accessTokenDeserializer.apply(requestToken);
                    if(accessToken != null){
                        return chain.filter(exchange);
                    }
                    RefreshToken refreshToken = refreshTokenDeserializer.apply(requestToken);
                    if(refreshToken != null){
                        return chain.filter(exchange);
                    }
                }
            }
            throw new RuntimeException("can't valid token");
        });
    }

    public JwtAuthenticationFilter(){
        super(Config.class);
    }

    public static class Config {

    }

}
