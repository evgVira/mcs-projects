package org.mcs.apigateway.security;

import lombok.RequiredArgsConstructor;
import org.mcs.apigateway.client.AuthenticationServiceClient;
import org.mcs.apigateway.dto.TokenValidationRequestDto;
import org.mcs.apigateway.dto.AccessToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TokenValidationService {

    private final AuthenticationServiceClient authenticationServiceClient;

    public AccessToken validToken(TokenValidationRequestDto tokenValidationRequestDto){
        return authenticationServiceClient.validTokenIntoAuthenticationService(tokenValidationRequestDto);
    }
}
