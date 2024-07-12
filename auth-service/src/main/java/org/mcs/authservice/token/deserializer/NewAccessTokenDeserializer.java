package org.mcs.authservice.token.deserializer;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.jwk.gen.OctetSequenceKeyGenerator;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.mcs.authservice.token.model.AccessToken;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.UUID;
import java.util.function.Function;

@Component
@Slf4j
public class NewAccessTokenDeserializer implements Function<String, AccessToken> {

    @Value("${jwt.access-secret}")
    private String accessSecretKey;

    private final JWSAlgorithm jwsAlgorithm = JWSAlgorithm.HS256;

    @Override
    public AccessToken apply(String string) {

        try {
            SecretKey secretKey = new OctetSequenceKeyGenerator(256)
                    .keyID(accessSecretKey)
                    .algorithm(jwsAlgorithm)
                    .generate()
                    .toSecretKey();

            var jwtClaims = Jwts.parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(string)
                    .getPayload();

            return AccessToken.builder()
                    .id(UUID.fromString(jwtClaims.getId()))
                    .subject(jwtClaims.getSubject())
                    .authorities(jwtClaims.getAudience().stream().toList())
                    .createdAt(jwtClaims.getIssuedAt().toInstant())
                    .expiresAt(jwtClaims.getExpiration().toInstant())
                    .build();

        } catch (JOSEException joseException) {
            log.error("Can't deserialize accessToken");
        }
        return null;
    }
}
