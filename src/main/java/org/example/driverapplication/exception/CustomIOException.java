package org.example.driverapplication.exception;

import lombok.Data;

@Data
public class CustomIOException extends Exception{

    String errorCode;
    public CustomIOException(String errorCode, String errorMessage) {
        super(errorMessage);
        this.errorCode = errorCode;

    }
}
