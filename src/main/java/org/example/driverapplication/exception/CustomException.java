package org.example.driverapplication.exception;

import lombok.Data;

@Data
public class CustomException extends Exception{

    String errorCode;
    public CustomException(String errorCode, String errorMessage) {
        super(errorMessage);
        this.errorCode = errorCode;
    }
    public CustomException(String errorMessage) {
        super(errorMessage);
    }
}

