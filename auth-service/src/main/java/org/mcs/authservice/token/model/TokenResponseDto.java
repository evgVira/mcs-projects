package org.mcs.authservice.token.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TokenResponseDto {

    private String accessToken;

    private String refreshToken;
}
