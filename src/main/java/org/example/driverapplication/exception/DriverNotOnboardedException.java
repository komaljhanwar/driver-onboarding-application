package org.example.driverapplication.exception;

import lombok.Data;

@Data
public class DriverNotOnboardedException extends Exception {
    String errorCode;
    public DriverNotOnboardedException(String errorCode, String errorMessage) {
        super(errorMessage);
        this.errorCode = errorCode;
    }
    public DriverNotOnboardedException(String errorMessage) {
        super(errorMessage);
    }
}
