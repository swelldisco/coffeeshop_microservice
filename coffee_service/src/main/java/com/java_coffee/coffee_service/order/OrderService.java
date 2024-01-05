package com.java_coffee.coffee_service.order;

import java.util.List;

import com.java_coffee.coffee_service.coffee.CoffeeDto;

public interface OrderService {
    
    OrderDto createOrder(OrderDto orderDto);
    OrderDto getOrderById(long orderId);
    List<OrderDto> getOrdersByUserId(long userId);
    OrderDto updateOrder(long orderId, OrderDto orderDto);
    void cancelOrder(long orderId);

    void addCoffeeToOrder(Order coffeeOrder, CoffeeDto coffeeDto);

}
