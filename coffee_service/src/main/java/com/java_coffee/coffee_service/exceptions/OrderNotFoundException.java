package com.java_coffee.coffee_service.exceptions;

public class OrderNotFoundException extends RuntimeException{

    private String message;

    public OrderNotFoundException() {
        message = "Order not found.";
    }

    public String getMessage() {
        return message;
    };
    
}
