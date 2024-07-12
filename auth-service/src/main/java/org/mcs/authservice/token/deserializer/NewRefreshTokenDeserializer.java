package org.mcs.authservice.token.deserializer;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.jwk.gen.OctetSequenceKeyGenerator;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.mcs.authservice.token.model.RefreshToken;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.UUID;
import java.util.function.Function;

@Component
@Slf4j
public class NewRefreshTokenDeserializer implements Function<String, RefreshToken> {

    @Value("${jwt.refresh-secret}")
    private String refreshSecretKey;

    private final JWSAlgorithm jwsAlgorithm = JWSAlgorithm.HS256;

    @Override
    public RefreshToken apply(String string) {

        try {
            SecretKey secretKey = new OctetSequenceKeyGenerator(256)
                    .keyID(refreshSecretKey)
                    .algorithm(jwsAlgorithm)
                    .generate()
                    .toSecretKey();

            var jwtClaims = Jwts.parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(string)
                    .getPayload();

            return RefreshToken.builder()
                    .id(UUID.fromString(jwtClaims.getId()))
                    .subject(jwtClaims.getSubject())
                    .authorities(jwtClaims.getAudience().stream().toList())
                    .createdAt(jwtClaims.getIssuedAt().toInstant())
                    .expiresAt(jwtClaims.getExpiration().toInstant())
                    .build();
        } catch (JOSEException joseException) {
            log.error("Can't parse RefreshToken");
        }
        return null;
    }
}
