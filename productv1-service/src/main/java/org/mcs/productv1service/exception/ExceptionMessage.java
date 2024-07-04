package org.mcs.productv1service.exception;

import lombok.Getter;
import lombok.ToString;

@ToString
public enum ExceptionMessage {

    NULL_POINTER("arguments was not found"),

    ILLEGAL_ARGUMENT("arguments was not valid"),

    BAD_REQUEST("request was not valid");

    @Getter
    private String message;


    ExceptionMessage(String message) {
        this.message = message;
    }
}
