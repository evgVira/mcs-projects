package org.mcs.productv1service.exception;

import lombok.Getter;

@Getter
public class RestException extends RuntimeException {

    private Long proudctId;

    public RestException(String message) {
        super(message);
    }

    public RestException(String message, Long proudctId){
        super(message);
        this.proudctId = proudctId;
    }
}
