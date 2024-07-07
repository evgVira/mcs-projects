package org.mcs.authservice.controller;

import lombok.RequiredArgsConstructor;
import org.mcs.authservice.security.service.CreateTokenService;
import org.mcs.authservice.token.model.CreateTokenRequestDto;
import org.mcs.authservice.token.model.TokenResponseDto;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.nio.file.AccessDeniedException;

@RestController
@RequiredArgsConstructor
public class TokenCreateController {

    private final CreateTokenService createTokenService;

    @PostMapping("/token/create")
    public TokenResponseDto createToken(@RequestBody CreateTokenRequestDto createTokenRequestDto) throws AccessDeniedException {
        return createTokenService.createToken(createTokenRequestDto);
    }
}
