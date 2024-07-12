package org.mcs.authservice.token.serializer;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.jwk.gen.OctetSequenceKeyGenerator;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.mcs.authservice.token.model.RefreshToken;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
@Slf4j
public class NewRefreshTokenSerializer implements Function<RefreshToken, String> {

    @Value("${jwt.refresh-secret}")
    private String refreshTokenSecret;

    private final JWSAlgorithm jwsAlgorithm = JWSAlgorithm.HS256;

    @Override
    public String apply(RefreshToken refreshToken)  {
        Map<String, Object> claims = new HashMap<>();
        claims.put("roles", refreshToken.getAuthorities());

        try{
            SecretKey secretKey = new OctetSequenceKeyGenerator(256)
                    .keyID(refreshTokenSecret)
                    .algorithm(jwsAlgorithm)
                    .generate()
                    .toSecretKey();

            return Jwts.builder()
                    .claims(claims)
                    .subject(refreshToken.getSubject())
                    .issuedAt(Date.from(refreshToken.getCreatedAt()))
                    .expiration(Date.from(refreshToken.getExpiresAt()))
                    .signWith(secretKey)
                    .compact();

        }catch (JOSEException joseException){
            log.error("Can't serialize RefreshToken");
        }
        return null;
    }
}
