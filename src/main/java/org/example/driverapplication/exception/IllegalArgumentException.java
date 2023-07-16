package org.example.driverapplication.exception;

import lombok.Data;

@Data
public class IllegalArgumentException extends Exception{
    String errorCode;
    public IllegalArgumentException(String errorCode, String errorMessage) {
        super(errorMessage);
        this.errorCode = errorCode;

    }

    public IllegalArgumentException(String errorMessage) {
        super(errorMessage);
    }
}
