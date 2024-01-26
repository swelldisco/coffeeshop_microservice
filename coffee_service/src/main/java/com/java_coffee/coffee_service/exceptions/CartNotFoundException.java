package com.java_coffee.coffee_service.exceptions;

public class CartNotFoundException extends RuntimeException{

    private String message;

    public CartNotFoundException() {
        message = "No cart found.";
    }

    public String getMessage() {
        return message;
    }
    
}
