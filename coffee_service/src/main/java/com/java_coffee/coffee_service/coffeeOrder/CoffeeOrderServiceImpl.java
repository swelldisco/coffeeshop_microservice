package com.java_coffee.coffee_service.coffeeOrder;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.java_coffee.coffee_service.exceptions.CartMismatchException;
import com.java_coffee.coffee_service.exceptions.CartNotFoundException;
import com.java_coffee.coffee_service.exceptions.OrderNotFoundException;
import com.java_coffee.coffee_service.exceptions.RepositoryEmptyException;
import com.java_coffee.coffee_service.mapper.CoffeeMapper;
import com.java_coffee.coffee_service.pojo.OrderReceipt;
import com.java_coffee.coffee_service.pojo.UserStub;

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
        CoffeeOrder order = new CoffeeOrder(mapper.mapToCoffeeOrder(coffeeOrderDto));
        order.setLineItemTotal();
        return mapper.mapToCoffeeOrderDto(repo.save(order));
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
            throw new OrderNotFoundException();
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
            updatedOrder.setIsPaid(coffeeOrderDto.isPaid());
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

    @Override
    public double orderTotal(long cartId) {
        double total = 0.00;
        List<CoffeeOrder> currentOrders = repo.findUnpaidOrderByCartId(cartId);
        if (currentOrders != null && !currentOrders.isEmpty()) {
            for (CoffeeOrder order : currentOrders) {
                total += (order.getLineItemTotal());
            }
        }
        return total;
    }

    @Override
    public OrderReceipt generateReceipt(UserStub userStub, long cartId) {
        if (confirmCartOwnership(userStub.getUserId(), cartId)) {
            List<CoffeeOrderDto> order = repo.findUnpaidOrderByCartId(cartId).stream()
                .map(o -> mapper.mapToCoffeeOrderDto(o))
                .toList();
            return new OrderReceipt(userStub, order, orderTotal(cartId));
        } else {
            throw new CartMismatchException();
        }
    }

    private CoffeeOrder checkOrderByOrderId(long orderId) {
        return repo.findById(orderId)
            .orElseThrow(() -> new OrderNotFoundException());
    }

    private boolean confirmCartOwnership(long userId, long cartId) {
        if (repo.findFirstByCartId(cartId).isPresent()) {
            if (repo.findFirstByCartId(cartId).get().getUserId() == userId) {
                return true;
            }
        }
        return false;
    }
    
}
