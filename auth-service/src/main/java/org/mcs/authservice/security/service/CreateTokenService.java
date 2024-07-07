package org.mcs.authservice.security.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mcs.authservice.security.config.PasswordEncoderConfig;
import org.mcs.authservice.token.factory.AccessTokenFactory;
import org.mcs.authservice.token.factory.RefreshTokenFactory;
import org.mcs.authservice.token.model.AccessToken;
import org.mcs.authservice.token.model.CreateTokenRequestDto;
import org.mcs.authservice.token.model.RefreshToken;
import org.mcs.authservice.token.model.TokenResponseDto;
import org.mcs.authservice.token.serializer.AccessTokenSerializer;
import org.mcs.authservice.token.serializer.RefreshTokenSerializer;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.nio.file.AccessDeniedException;

@Component
@RequiredArgsConstructor
@Slf4j
public class CreateTokenService {

    private final PasswordEncoderConfig passwordEncoderConfig;

    private final AccessTokenFactory accessTokenFactory;

    private final RefreshTokenFactory refreshTokenFactory;

    private final AccessTokenSerializer accessTokenSerializer;

    private final RefreshTokenSerializer refreshTokenSerializer;

    private final UserEntityDetailsService userEntityDetailsService;

    public TokenResponseDto createToken(CreateTokenRequestDto createTokenRequestDto) throws AccessDeniedException {

        PasswordEncoder bcryptPasswordEncoder = passwordEncoderConfig.passwordEncoder();

        UserDetails userDetails = userEntityDetailsService.loadUserByUsername(createTokenRequestDto.getUsername());

        if (createTokenRequestDto.getUsername().equals(userDetails.getUsername()) && bcryptPasswordEncoder.matches(createTokenRequestDto.getPassword(), userDetails.getPassword())) {

            RefreshToken refreshToken = refreshTokenFactory.apply(userDetails);
            AccessToken accessToken = accessTokenFactory.apply(refreshToken);
            String refreshTokenToString = refreshTokenSerializer.apply(refreshToken);
            String accessTokenToString = accessTokenSerializer.apply(accessToken);

            return new TokenResponseDto(accessTokenToString, refreshTokenToString);
        }else {
            log.error("user not authenticated");
            throw new AccessDeniedException("User must be authenticated");
        }
    }

}
