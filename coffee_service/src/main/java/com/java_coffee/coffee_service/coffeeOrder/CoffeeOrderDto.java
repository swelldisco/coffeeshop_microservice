package com.java_coffee.coffee_service.coffeeOrder;

import java.time.LocalDateTime;

import com.java_coffee.coffee_service.coffee.CoffeeDto;

public record CoffeeOrderDto(long orderId, CoffeeDto coffeeDto, long cartId, long userId, int quantity, double lineItemTotal, LocalDateTime timeStamp, boolean isPaid) {}
