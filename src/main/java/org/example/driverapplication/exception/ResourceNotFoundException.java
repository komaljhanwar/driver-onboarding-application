package org.example.driverapplication.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Data
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    String errorCode;
    public ResourceNotFoundException(String errorCode, String errorMessage) {
        super(errorMessage);
        this.errorCode = errorCode;

    }
}

