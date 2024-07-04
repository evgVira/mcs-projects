package org.mcs.userv1service.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class BaseControllerAdvice {

    @ExceptionHandler(RestException.class)
    public ResponseEntity<ErrorDto> handleException(RestException exception){
        log.warn(String.format("user to id: %d was not found", exception.getUserId()));
        ErrorDto errorDto = new ErrorDto(HttpStatus.NOT_FOUND, exception.getMessage());
        return new ResponseEntity<>(errorDto, HttpStatus.NOT_FOUND);
    }
}
