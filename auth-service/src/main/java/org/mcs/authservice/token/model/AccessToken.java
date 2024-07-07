package org.mcs.authservice.token.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class AccessToken implements Token{

    private UUID id;

    private String subject;

    private List<String> authorities;

    private Instant createdAt;

    private Instant expiresAt;
}
