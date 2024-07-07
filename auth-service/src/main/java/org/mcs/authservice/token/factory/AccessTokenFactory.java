package org.mcs.authservice.token.factory;

import org.mcs.authservice.token.model.AccessToken;
import org.mcs.authservice.token.model.RefreshToken;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.function.Function;

@Component
public class AccessTokenFactory implements Function<RefreshToken, AccessToken> {

    private Duration tokenTtl = Duration.ofMinutes(5);


    @Override
    public AccessToken apply(RefreshToken refreshToken) {

        List<String> authorities = refreshToken.getAuthorities().stream()
                .filter(authority -> authority.startsWith("GRANT"))
                .map(authority -> authority.split("GRANT_")[1])
                .toList();

        Instant createdAt = Instant.now();
        Instant expiresAt = createdAt.plus(tokenTtl);

        return AccessToken.builder()
                .id(refreshToken.getId())
                .subject(refreshToken.getSubject())
                .authorities(authorities)
                .createdAt(createdAt)
                .expiresAt(expiresAt)
                .build();
    }
}
