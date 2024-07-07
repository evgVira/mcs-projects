package org.mcs.authservice.token.serializer;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mcs.authservice.token.config.AccessVerifyConfig;
import org.mcs.authservice.token.model.AccessToken;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.function.Function;

@Component
@RequiredArgsConstructor
@Slf4j
@Qualifier("accessConfigBean")
public class AccessTokenSerializer implements Function<AccessToken, String> {

    private final AccessVerifyConfig accessVerifyConfig;

    @Override
    public String apply(AccessToken accessToken) {

        JWSHeader jwsHeader = new JWSHeader.Builder(accessVerifyConfig.getJwsAlgorithm())
                .keyID(accessToken.getId().toString())
                .build();

        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .jwtID(accessToken.getId().toString())
                .subject(accessToken.getSubject())
                .claim("authorities", accessToken.getAuthorities())
                .issueTime(Date.from(accessToken.getCreatedAt()))
                .expirationTime(Date.from(accessToken.getExpiresAt()))
                .build();

        SignedJWT signedJWT = new SignedJWT(jwsHeader, jwtClaimsSet);

        try {

            JWSSigner jwsSigner = new MACSigner(accessVerifyConfig.cryptSecret());

            signedJWT.sign(jwsSigner);

            String accessTokenToString = signedJWT.serialize();

            return accessTokenToString;
        } catch (JOSEException joseException) {
            log.error(joseException.getMessage());
        }
        return null;
    }
}
