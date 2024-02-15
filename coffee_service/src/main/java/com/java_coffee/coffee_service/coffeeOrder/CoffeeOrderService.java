package com.java_coffee.coffee_service.coffeeOrder;

import java.util.List;

import com.java_coffee.coffee_service.pojo.OrderReceipt;
import com.java_coffee.coffee_service.pojo.UserStub;

public interface CoffeeOrderService {
    
    CoffeeOrderDto createOrder(CoffeeOrderDto coffeeOrderDto);
    CoffeeOrderDto getOrderByOrderId(long orderId);
    List<CoffeeOrderDto> getOrderByCartId(long cartId);
    List<CoffeeOrderDto> getOrderByUserId(long userId);
    List<CoffeeOrderDto> getAllOrders();
    CoffeeOrderDto updateOrder(long orderId, CoffeeOrderDto coffeeOrderDto);
    void deleteOrderById(long orderId);
    double orderTotal(long cartId);
    OrderReceipt generateReceipt(UserStub userStub, long cartId);

}
