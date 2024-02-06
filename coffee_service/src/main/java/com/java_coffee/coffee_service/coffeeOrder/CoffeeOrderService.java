package com.java_coffee.coffee_service.coffeeOrder;

import java.util.List;

public interface CoffeeOrderService {
    
    CoffeeOrderDto createOrder(CoffeeOrderDto coffeeOrderDto);
    CoffeeOrderDto getOrderByOrderId(long orderId);
    List<CoffeeOrderDto> getOrderByCartId(long cartId);
    List<CoffeeOrderDto> getOrderByUserId(long userId);
    List<CoffeeOrderDto> getAllOrders();
    CoffeeOrderDto updateOrder(long orderId, CoffeeOrderDto coffeeOrderDto);
    void deleteOrderById(long orderId);

}
