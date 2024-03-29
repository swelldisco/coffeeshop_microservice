package com.java_coffee.coffee_service.coffee;

import java.util.List;

import com.java_coffee.coffee_service.coffee.constants.CoffeeSize;

public record CoffeeDto(long coffeeId, CoffeeSize coffeeSize, String drinkName, double basePrice, double price, List<String> ingredientList) {}
