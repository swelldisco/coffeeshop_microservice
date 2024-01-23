package com.java_coffee.coffee_service.order;

import java.time.LocalDateTime;
import java.util.List;
import com.java_coffee.coffee_service.coffee.CoffeeDto;

public record OrderDto (long orderId, long userId, List<CoffeeDto> myOrder, LocalDateTime timeStamp) {}
