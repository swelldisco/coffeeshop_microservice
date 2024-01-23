package com.java_coffee.coffee_service.order;

import java.util.List;

import org.springframework.stereotype.Service;

import com.java_coffee.coffee_service.coffee.Coffee;
import com.java_coffee.coffee_service.coffee.CoffeeDto;
import com.java_coffee.coffee_service.order.exceptions.OrderNotFoundException;

@Service
public class OrderServiceImpl implements OrderService{

    private OrderMapper mapper = new OrderMapper();
    private OrderRepository repo;

    @Override
    public OrderDto createOrder(OrderDto orderDto) {
        return mapper.mapToDto(repo.save(mapper.mapToOrder(orderDto)));
    }

    @Override
    public OrderDto getOrderById(long orderId) {
        return mapper.mapToDto(checkOptionalOrder(orderId));
    }

    @Override
    public List<OrderDto> getOrdersByUserId(long userId) {
        if (repo.findAllByUserId(userId) != null && !repo.findAllByUserId(userId).isEmpty()) {
            return repo.findAllByUserId(userId).stream()
                .map(o -> mapper.mapToDto(o))
                .toList();
        } else {
            throw new OrderNotFoundException();
        }
    }

    @Override
    public OrderDto updateOrder(long orderId, OrderDto orderDto) {
        Order updatedOrder = checkOptionalOrder(orderId);
        updatedOrder.setMyOrder(mapper.mapListToCoffee(orderDto.myOrder()));
        return mapper.mapToDto(repo.save(updatedOrder));
    }

    @Override
    public void cancelOrder(long orderId) {
        if (repo.existsByOrderId(orderId)) {
            repo.deleteByOrderId(orderId);
        } else {
            throw new OrderNotFoundException();
        }
    }

    @Override
    public void addCoffeeToOrder(Order coffeeOrder, CoffeeDto coffeeDto) {
       coffeeOrder.addCoffeeToOrder(new Coffee(coffeeDto.coffeeId(), coffeeDto.size(), coffeeDto.drinkName(), coffeeDto.basePrice(), coffeeDto.ingredientList()));
    }

    private Order checkOptionalOrder(long orderId) {
        return repo.findById(orderId)
            .orElseThrow(() -> new OrderNotFoundException());
    }
    
}
