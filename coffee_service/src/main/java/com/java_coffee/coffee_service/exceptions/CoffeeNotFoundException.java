package com.java_coffee.coffee_service.exceptions;

public class CoffeeNotFoundException extends RuntimeException {
    
    private String message;
   
    public CoffeeNotFoundException() {
        this.message = "No Coffee with that ID found";
    }

    public CoffeeNotFoundException(String drinkName) {
        this.message = drinkName + " not found.";
    }

    public String getMessage() {
        return message;
    }
}
