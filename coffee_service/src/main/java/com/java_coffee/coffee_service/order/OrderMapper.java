package com.java_coffee.coffee_service.order;

public class OrderMapper {
    public Order mapToOrder(OrderDto source) {
        return new Order(
            source.returnOrderList(),
            source.getOrderId(),
            source.getUserId(),
            source.getTimeStamp()
        );
    }

    public OrderDto mapToDto(Order source) {
        return new OrderDto(
            source.returnOrderList(),
            source.getOrderId(),
            source.getUserId(),
            source.getTimeStamp()
        );
    }
}
