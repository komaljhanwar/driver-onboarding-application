package org.example.driverapplication.exception;

import lombok.Data;

@Data
public class InternalServerErrorException extends Exception{
    String errorCode;
    public InternalServerErrorException(String errorCode, String errorMessage) {
        super(errorMessage);
        this.errorCode = errorCode;

    }
}
