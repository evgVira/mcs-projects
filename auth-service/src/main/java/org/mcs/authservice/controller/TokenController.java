package org.mcs.authservice.controller;

import lombok.RequiredArgsConstructor;
import org.mcs.authservice.dto.TokenValidationRequestDto;
import org.mcs.authservice.security.service.CreateTokenService;
import org.mcs.authservice.service.TokenValidationService;
import org.mcs.authservice.token.model.AccessToken;
import org.mcs.authservice.token.model.CreateTokenRequestDto;
import org.mcs.authservice.token.model.Token;
import org.mcs.authservice.token.model.TokenResponseDto;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/")
public class TokenController {

    private final TokenValidationService tokenValidationService;

    private final CreateTokenService createTokenService;

    @PostMapping("/auth/token/create")
    public TokenResponseDto createToken(@RequestBody CreateTokenRequestDto createTokenRequestDto) throws AccessDeniedException {
        return createTokenService.createToken(createTokenRequestDto);
    }

    @GetMapping("/auth/token/validation")
    public AccessToken validationTokenFromApiGatewayService(@RequestBody TokenValidationRequestDto tokenValidationRequestDto){
        return tokenValidationService.validationAccessToken(tokenValidationRequestDto.getRequestToken());
    }
}
