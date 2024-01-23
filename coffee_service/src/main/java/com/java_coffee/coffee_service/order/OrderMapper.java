package com.java_coffee.coffee_service.order;

import java.util.List;

import com.java_coffee.coffee_service.coffee.Coffee;
import com.java_coffee.coffee_service.coffee.CoffeeDto;
import com.java_coffee.coffee_service.coffee.CoffeeMapper;

public class OrderMapper {

    private CoffeeMapper coffeeMapper;

    public OrderMapper() {
        this.coffeeMapper = new CoffeeMapper();
    }

    public Order mapToOrder(OrderDto source) {
        return new Order(
            source.orderId(),
            source.userId(),
            mapListToCoffee(source.myOrder()),
            source.timeStamp()
        );
    }

    public OrderDto mapToDto(Order source) {
        return new OrderDto(
            source.getOrderId(),
            source.getUserId(),
            mapListToDto(source.getOrderList()),
            source.getTimeStamp()
        );
    }

    protected List<Coffee> mapListToCoffee(List<CoffeeDto> list) {
        return list.stream()
            .map(c -> coffeeMapper.mapToCoffee(c))
            .toList();
    }

    private List<CoffeeDto> mapListToDto(List<Coffee> list) {
        return list.stream()
            .map(c -> coffeeMapper.mapToDto(c))
            .toList();
    }

}
