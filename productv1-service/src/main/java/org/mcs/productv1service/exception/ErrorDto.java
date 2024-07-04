package org.mcs.productv1service.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Data
public class ErrorDto {

    private HttpStatus httpStatus;

    private String error;

}
