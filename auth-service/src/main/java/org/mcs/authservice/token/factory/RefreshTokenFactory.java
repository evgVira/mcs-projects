package org.mcs.authservice.token.factory;

import org.mcs.authservice.token.model.RefreshToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Function;

@Component
public class RefreshTokenFactory implements Function<UserDetails, RefreshToken> {

    private final Duration tokenTtl = Duration.ofDays(1);

    @Override
    public RefreshToken apply(UserDetails userDetails) {

        List<String> authorities = new ArrayList<>();

        authorities.add("JWT_REFRESH");
        authorities.add("JWT_LOGOUT");

        userDetails.getAuthorities().stream()
                .map("GRANT_%s"::formatted)
                .forEach(authorities::add);

        Instant createdAt = Instant.now();
        Instant expiresAt = createdAt.plus(tokenTtl);

        return RefreshToken.builder()
                .id(UUID.randomUUID())
                .subject(userDetails.getUsername())
                .authorities(authorities)
                .createdAt(createdAt)
                .expiresAt(expiresAt)
                .build();
    }
}
