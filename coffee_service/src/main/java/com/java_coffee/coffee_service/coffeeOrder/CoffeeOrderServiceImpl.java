package com.java_coffee.coffee_service.coffeeOrder;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.java_coffee.coffee_service.exceptions.CartNotFoundException;
import com.java_coffee.coffee_service.exceptions.OrderNotFoundException;
import com.java_coffee.coffee_service.exceptions.RepositoryEmptyException;
import com.java_coffee.coffee_service.mapper.CoffeeMapper;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CoffeeOrderServiceImpl implements CoffeeOrderService {

    @Autowired
    private CoffeeMapper mapper;
    
    @Autowired
    private CoffeeOrderRepository repo;

    @Override
    public CoffeeOrderDto createOrder(CoffeeOrderDto coffeeOrderDto) {
        return mapper.mapToCoffeeOrderDto(repo.save(mapper.mapToCoffeeOrder(coffeeOrderDto)));
    }

    @Override
    public CoffeeOrderDto getOrderByOrderId(long orderId) {
        return mapper.mapToCoffeeOrderDto(checkOrderByOrderId(orderId));
    }

    @Override
    public List<CoffeeOrderDto> getOrderByCartId(long cartId) {
        if (repo.findByCartId(cartId) != null && !repo.findByCartId(cartId).isEmpty()) {
            return repo.findByCartId(cartId).stream()
                .map(o -> mapper.mapToCoffeeOrderDto(o))
                .toList();
        } else {
            throw new CartNotFoundException();
        }
    }

    @Override
    public List<CoffeeOrderDto> getOrderByUserId(long userId) {
        if (repo.findByUserId(userId) != null && !repo.findByUserId(userId).isEmpty()) {
            return repo.findByUserId(userId).stream()
                .map(o -> mapper.mapToCoffeeOrderDto(o))
                .toList();
        } else {
            throw new CartNotFoundException();
        }
    }

    @Override
    public List<CoffeeOrderDto> getAllOrders() {
        if (repo.findAll() != null && !repo.findAll().isEmpty()) {
            return repo.findAll().stream()
                .map(o -> mapper.mapToCoffeeOrderDto(o))
                .toList();
        } else {
            throw new RepositoryEmptyException();
        }
    }

    @Override
    public CoffeeOrderDto updateOrder(long orderId, CoffeeOrderDto coffeeOrderDto) {
        if (repo.existsByOrderId(orderId)) {
            CoffeeOrder updatedOrder = checkOrderByOrderId(orderId);
            updatedOrder.setCoffee(mapper.mapToCoffee(coffeeOrderDto.coffeeDto()));
            updatedOrder.setQuantity(coffeeOrderDto.quantity());
            return mapper.mapToCoffeeOrderDto(repo.save(updatedOrder));
        } else {
            throw new OrderNotFoundException();
        }
    }

    @Override
    public void deleteOrderById(long orderId) {
        if (repo.existsByOrderId(orderId)) {
            repo.deleteCoffeeOrderByOrderId(orderId);
        } else {
            throw new OrderNotFoundException();
        }
    }

    private CoffeeOrder checkOrderByOrderId(long orderId) {
        return repo.findById(orderId)
            .orElseThrow(() -> new OrderNotFoundException());
    }
    
}
