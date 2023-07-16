package org.example.driverapplication.exception.handler;

import org.example.driverapplication.exception.*;
import org.example.driverapplication.exception.IllegalArgumentException;
import org.example.driverapplication.response.ErrrorBaseResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class ExceptionHandler extends ResponseEntityExceptionHandler {
    @org.springframework.web.bind.annotation.ExceptionHandler(value = {InternalServerErrorException.class, CustomIOException.class})
    protected ResponseEntity<Object> handleInternalServerException(InternalServerErrorException ex, WebRequest webRequest) {
        ErrrorBaseResponse inclusiveException = ErrrorBaseResponse.builder()
                .errorCode(ex.getErrorCode())
                .errorMessage(ex.getMessage())
                .build();
        return new ResponseEntity(inclusiveException, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }


    /*@org.springframework.web.bind.annotation.ExceptionHandler(NotFoundException.class)
    protected ResponseEntity<Object> handleNotFoundException(NotFoundException ex, WebRequest webRequest) {
        ErrrorBaseResponse inclusiveException = ErrrorBaseResponse.builder()
                .errorCode(ex.getErrorCode())
                .errorMessage(ex.getMessage())
                .build();
        return new ResponseEntity(inclusiveException, new HttpHeaders(), HttpStatus.NOT_FOUND);
    }*/

    @org.springframework.web.bind.annotation.ExceptionHandler(IllegalArgumentException.class)
    protected ResponseEntity<Object> handleIllegalArgumentException(IllegalArgumentException ex, WebRequest webRequest) {
        ErrrorBaseResponse inclusiveException = ErrrorBaseResponse.builder()
                .errorCode(ex.getErrorCode())
                .errorMessage(ex.getMessage())
                .build();
        return new ResponseEntity(inclusiveException, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(TokenMissingException.class)
    protected ResponseEntity<Object> handleIOException(TokenMissingException ex, WebRequest webRequest) {
        ErrrorBaseResponse inclusiveException = ErrrorBaseResponse.builder()
                .errorCode(ex.getErrorCode())
                .errorMessage(ex.getMessage())
                .build();
        return new ResponseEntity(inclusiveException, new HttpHeaders(), HttpStatus.UNAUTHORIZED);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(DriverNotOnboardedException.class)
    protected ResponseEntity<Object> handleServiceException(DriverNotOnboardedException ex, WebRequest webRequest) {
        ErrrorBaseResponse inclusiveException = ErrrorBaseResponse.builder()
                .errorCode(ex.getErrorCode())
                .errorMessage(ex.getMessage())
                .build();
        return new ResponseEntity(inclusiveException, new HttpHeaders(), HttpStatus.NOT_FOUND);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(CustomException.class)
    protected ResponseEntity<Object> handleCustomException(CustomException ex, WebRequest webRequest) {
        ErrrorBaseResponse inclusiveException = ErrrorBaseResponse.builder()
                .errorCode(ex.getErrorCode())
                .errorMessage(ex.getMessage())
                .build();
        return new ResponseEntity(inclusiveException, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(ResourceNotFoundException.class)
    protected ResponseEntity<Object> handleResourceNotFoundException(ResourceNotFoundException ex, WebRequest webRequest) {
        ErrrorBaseResponse inclusiveException = ErrrorBaseResponse.builder()
                .errorCode(ex.getErrorCode())
                .errorMessage(ex.getMessage())
                .build();
        return new ResponseEntity(inclusiveException, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(BGVFailedException.class)
    protected ResponseEntity<Object> handleBGVFailedException(BGVFailedException ex, WebRequest webRequest) {
        ErrrorBaseResponse inclusiveException = ErrrorBaseResponse.builder()
                .errorCode(ex.getErrorCode())
                .errorMessage(ex.getMessage())
                .build();
        return new ResponseEntity(inclusiveException, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request) {
        String message="";
        List<String> errors = new ArrayList<String>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.add(error.getField() + ": " + error.getDefaultMessage());
            message= error.getField() + ": " + error.getDefaultMessage();
        }
        for (ObjectError error : ex.getBindingResult().getGlobalErrors()) {
            errors.add(error.getObjectName() + ": " + error.getDefaultMessage());
            message=error.getObjectName() + ": " + error.getDefaultMessage();
        }

        ErrrorBaseResponse apiError = ErrrorBaseResponse.builder()
                .errorCode(HttpStatus.BAD_REQUEST.name())
                .errorMessage(message).build();
        return handleExceptionInternal(ex, apiError, headers, HttpStatus.OK, request);
    }
}
