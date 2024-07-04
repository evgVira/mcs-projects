package org.mcs.userv1service.exception;

import lombok.Getter;
import lombok.ToString;

@ToString
public enum ExceptionMessage {

    NULL_POINTER("arguments was not found");

    @Getter
    private String message;

    ExceptionMessage(String message) {
        this.message = message;
    }
}
