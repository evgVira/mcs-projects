package org.mcs.authservice.token.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CreateTokenRequestDto {

    private String username;

    private String password;
}
