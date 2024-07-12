package org.mcs.apigateway.dto;

import jakarta.ws.rs.GET;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TokenValidationRequestDto {

    private String requestToken;
}
