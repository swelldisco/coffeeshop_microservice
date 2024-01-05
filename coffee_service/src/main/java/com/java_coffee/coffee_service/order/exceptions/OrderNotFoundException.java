package com.java_coffee.coffee_service.order.exceptions;

public class OrderNotFoundException extends RuntimeException{
    private String message;

    public OrderNotFoundException() {
        this.message = "Order not found";
    }

    public String getMessage() {
        return message;
    }
}
