package com.java_coffee.coffee_service.cart;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.java_coffee.coffee_service.coffeeOrder.CoffeeOrder;
import com.java_coffee.coffee_service.coffeeOrder.CoffeeOrderDto;
import com.java_coffee.coffee_service.coffeeOrder.CoffeeOrderService;
import com.java_coffee.coffee_service.exceptions.CartMismatchException;
import com.java_coffee.coffee_service.exceptions.CartNotFoundException;
import com.java_coffee.coffee_service.exceptions.OrderNotFoundException;
import com.java_coffee.coffee_service.mapper.CoffeeMapper;
import com.java_coffee.coffee_service.userStub.UserStub;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CartServiceImpl implements CartService{
    @Autowired
    private CoffeeMapper mapper;
    @Autowired
    private CartRepository repo;
    @Autowired
    private CoffeeOrderService orderService;

    @Override
    public CartDto createCart(UserStub userStub) {
        Cart cart = new Cart(userStub);
        return mapper.mapToCartDto(repo.save(cart));
    }

    @Override
    public CartDto getCartById(long cartId) {
        return mapper.mapToCartDto(digThroughOptionalCart(cartId));
    }

    @Override
    public CartDto getCartByUserId(long userId) {
        return mapper.mapToCartDto(rudelyInvestigateAUsersCart(userId));
    }

    @Override
    public List<CartDto> getAllCarts() {
        if (repo.findAll() != null && !repo.findAll().isEmpty()) {
            return repo.findAll().stream()
                .map(c -> mapper.mapToCartDto(c))
                .toList();
        } else {
            throw new CartNotFoundException();
        }
    }

    @Override
    public CartDto addItemToCart(long cartId, CoffeeOrderDto coffeeOrderDto) {
        if (repo.existsByCartId(cartId)) {
            if (coffeeOrderDto.cartId() == cartId) {
                Cart cart = digThroughOptionalCart(cartId);
                cart.addOrderToCart(mapper.mapToCoffeeOrder(coffeeOrderDto));
                return mapper.mapToCartDto(repo.save(cart));
            } else {
                throw new CartMismatchException();
            }
        } else {
            throw new CartNotFoundException();
        }
    }

    @Override
    public CartDto removeItemFromCart(long cartId, CoffeeOrderDto coffeeOrderDto) {
        if (repo.existsByCartId(cartId)) {
            Cart cart = digThroughOptionalCart(cartId);
            CoffeeOrder order = new CoffeeOrder(mapper.mapToCoffeeOrder(orderService.getOrderByOrderId(coffeeOrderDto.orderId())));
            if (cart.getOrders().contains(order)) {
                cart.removeOrderFromCart(order);
                orderService.deleteOrderById(coffeeOrderDto.orderId());
                return mapper.mapToCartDto(repo.save(cart));
            } else {
                throw new OrderNotFoundException();
            }
        } else {
            throw new CartNotFoundException();
        }
    }

    @Override
    public CartDto clearCart(long cartId) {
        if (repo.existsByCartId(cartId)) {
            Cart cart = digThroughOptionalCart(cartId);
            if (cart.getOrders() != null && !cart.getOrders().isEmpty()) {
                List<Long> orderIds = cart.getOrders().stream()
                    .map(o -> o.getOrderId())
                    .toList();
                cart.emptyCart();
                for (long orderId : orderIds) {
                    orderService.deleteOrderById(orderId);
                }
            }
            return mapper.mapToCartDto(repo.save(cart));
        } else {
            throw new CartNotFoundException();
        }
    }

    @Override
    public void deleteCartById(long cartId) {
        if (repo.existsByCartId(cartId)) {
            List<Long> orderIds = orderService.getOrderByCartId(cartId).stream()
                .map(o -> o.orderId())
                .toList();
            if (orderIds != null && !orderIds.isEmpty()) {
                for (long orderId : orderIds) {
                    orderService.deleteOrderById(orderId);
                }
            }
            repo.deleteByCartId(cartId);
        } else {
            throw new CartNotFoundException();
        }
    }

    private Cart digThroughOptionalCart(long cartId) {
        return repo.findById(cartId)
            .orElseThrow(() -> new CartNotFoundException());
    }

    private Cart rudelyInvestigateAUsersCart(long userId) {
        return repo.findCartByUserId(userId)
            .orElseThrow(() -> new CartNotFoundException());
    }
    
}
