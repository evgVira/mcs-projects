package org.mcs.userv1service.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
public class ErrorDto {

    private HttpStatus status;

    private String error;
}
