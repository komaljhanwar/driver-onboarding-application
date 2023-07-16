package org.example.driverapplication.exception;

import lombok.Data;

@Data
public class TokenMissingException extends Exception {
    String errorCode;
    public TokenMissingException(String errorCode, String errorMessage) {
        super(errorMessage);
        this.errorCode = errorCode;

    }
}
