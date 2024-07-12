package org.mcs.apigateway.security;


import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Predicate;

@Component
public class RouteValidator {

    public final List<String> openApiEndpoints = List.of("/token/create", "/user/update");

    public Predicate<ServerHttpRequest> isSecured = request -> openApiEndpoints.stream()
            .noneMatch(uri -> request.getURI().getPath().contains(uri));
}
