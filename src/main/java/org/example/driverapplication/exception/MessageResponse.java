package org.example.driverapplication.exception;

public class MessageResponse {

    private String message;

    public MessageResponse(String message) {
        this.message = message;
    }

    // getters and setters

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

