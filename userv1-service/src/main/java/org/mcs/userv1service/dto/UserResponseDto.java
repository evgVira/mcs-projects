package org.mcs.userv1service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserResponseDto {

    private Long id;

    private String username;

    private String email;

    private String password;

    private String createdDt;

    private String updatedDt;
}
