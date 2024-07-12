package org.mcs.authservice.token.serializer;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.jwk.gen.OctetSequenceKeyGenerator;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.mcs.authservice.token.model.AccessToken;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
@Slf4j
public class NewAccessTokenSerializer implements Function<AccessToken, String> {

    @Value("${jwt.access-secret}")
    private String accessSecret;

    private final JWSAlgorithm jwsAlgorithm = JWSAlgorithm.HS256;

    @Override
    public String apply(AccessToken accessToken) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("roles", accessToken.getAuthorities());

        try {
            SecretKey secretKey = new OctetSequenceKeyGenerator(256)
                    .keyID(accessSecret)
                    .algorithm(jwsAlgorithm)
                    .generate()
                    .toSecretKey();

            return Jwts.builder()
                    .claims(claims)
                    .subject(accessToken.getSubject())
                    .issuedAt(Date.from(accessToken.getCreatedAt()))
                    .expiration(Date.from(accessToken.getExpiresAt()))
                    .signWith(secretKey)
                    .compact();

        } catch (JOSEException joseException) {
            log.error("Can't serialize AccessToken");
        }
        return null;

    }
}
