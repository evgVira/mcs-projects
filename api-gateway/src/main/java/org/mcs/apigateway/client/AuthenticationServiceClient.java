package org.mcs.apigateway.client;

import org.mcs.apigateway.dto.AccessToken;
import org.mcs.apigateway.dto.TokenValidationRequestDto;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "auth-service", path = "http://localhost:8082/api/v1/auth")
public interface AuthenticationServiceClient {

    @GetMapping("/token/validation")
    @LoadBalanced
    AccessToken validTokenIntoAuthenticationService(@RequestBody TokenValidationRequestDto requestToken);
}
