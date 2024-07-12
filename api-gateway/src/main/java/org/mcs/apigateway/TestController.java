package org.mcs.apigateway;

import lombok.RequiredArgsConstructor;
import org.mcs.apigateway.dto.Token;
import org.mcs.apigateway.dto.TokenValidationRequestDto;
import org.mcs.apigateway.dto.AccessToken;
import org.mcs.apigateway.security.TokenValidationService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TestController {

    private final TokenValidationService tokenValidationService;

    @GetMapping("/test/client")
    public Token testClient(@RequestBody TokenValidationRequestDto tokenValidationRequestDto) {
        return tokenValidationService.validToken(tokenValidationRequestDto);
    }

}
