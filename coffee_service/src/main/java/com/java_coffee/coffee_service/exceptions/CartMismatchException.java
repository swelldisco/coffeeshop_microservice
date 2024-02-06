package com.java_coffee.coffee_service.exceptions;

public class CartMismatchException extends RuntimeException {
    
    private String message;

    public CartMismatchException() {
        message = "This order does not belong to your cart.";
    }

    public String getMessage() {
        return message;
    }
    
}
