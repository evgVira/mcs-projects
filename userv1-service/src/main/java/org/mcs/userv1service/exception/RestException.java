package org.mcs.userv1service.exception;

import lombok.Getter;

@Getter
public class RestException extends RuntimeException{

    private Long userId;

    public RestException(String message){
        super(message);
    }

    public RestException(String message, Long userId){
        super(message);
        this.userId = userId;
    }
}
