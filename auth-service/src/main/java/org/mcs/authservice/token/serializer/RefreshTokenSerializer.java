package org.mcs.authservice.token.serializer;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mcs.authservice.token.config.RefreshVerifyConfig;
import org.mcs.authservice.token.model.RefreshToken;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.function.Function;

@Component
@RequiredArgsConstructor
@Slf4j
@Qualifier("refreshConfigBean")
public class RefreshTokenSerializer implements Function<RefreshToken, String> {

    private final RefreshVerifyConfig refreshVerifyConfig;

    @Override
    public String apply(RefreshToken refreshToken) {

        JWSHeader jwsHeader = new JWSHeader.Builder(refreshVerifyConfig.getJwsAlgorithm())
                .keyID(refreshToken.getId().toString())
                .build();

        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .jwtID(refreshToken.getId().toString())
                .subject(refreshToken.getSubject())
                .claim("authorities", refreshToken.getAuthorities())
                .issueTime(Date.from(refreshToken.getCreatedAt()))
                .expirationTime(Date.from(refreshToken.getExpiresAt()))
                .build();

        SignedJWT signedJWT = new SignedJWT(jwsHeader, jwtClaimsSet);

        try {

            JWSSigner jwsSigner = new MACSigner(refreshVerifyConfig.cryptSecret());

            signedJWT.sign(jwsSigner);

            String refreshTokenToString = signedJWT.serialize();

            return refreshTokenToString;

        } catch (JOSEException joseException) {
            log.error(joseException.getMessage());
        }

        return null;
    }
}
