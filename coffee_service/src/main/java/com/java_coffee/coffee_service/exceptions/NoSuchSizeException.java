package com.java_coffee.coffee_service.exceptions;

public class NoSuchSizeException extends RuntimeException {

    private String message;

    public NoSuchSizeException() {
        message = "No such drink size";
    }

    public String getMessage() {
        return message;
    }
    
}
