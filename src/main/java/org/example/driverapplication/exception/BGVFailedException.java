package org.example.driverapplication.exception;

import lombok.Data;

@Data
public class BGVFailedException extends Exception{

    String errorCode;
    public BGVFailedException(String errorCode, String errorMessage) {
        super(errorMessage);
        this.errorCode = errorCode;

    }
}
