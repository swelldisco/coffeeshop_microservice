package com.java_coffee.coffee_service.cart;

import java.util.List;

import com.java_coffee.coffee_service.coffeeOrder.CoffeeOrderDto;

public record CartDto(long cartId, long userId, List<CoffeeOrderDto> orders) {
    
}
