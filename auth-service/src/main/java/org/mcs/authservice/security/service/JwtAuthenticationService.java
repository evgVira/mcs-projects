package org.mcs.authservice.security.service;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mcs.authservice.token.deserializer.AccessTokenDeserializer;
import org.mcs.authservice.token.deserializer.RefreshTokenDeserializer;
import org.mcs.authservice.token.model.AccessToken;
import org.mcs.authservice.token.model.RefreshToken;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationConverter;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationService implements AuthenticationConverter {

    private final AccessTokenDeserializer accessTokenDeserializer;

    private final RefreshTokenDeserializer refreshTokenDeserializer;

    @Override
    public Authentication convert(HttpServletRequest request) {

        String header = request.getHeader(HttpHeaders.AUTHORIZATION);

        if(header != null && header.startsWith("Bearer ")){

            String requestToken = header.split("Bearer ")[1];

            AccessToken accessToken = accessTokenDeserializer.apply(requestToken);
            if(accessToken != null){
                return new PreAuthenticatedAuthenticationToken(accessToken, requestToken);
            }

            RefreshToken refreshToken = refreshTokenDeserializer.apply(requestToken);
            if(refreshToken != null){
                return new PreAuthenticatedAuthenticationToken(refreshToken, requestToken);
            }
        }
        return null;
    }
}
