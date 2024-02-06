package com.java_coffee.coffee_service.cart;

import java.util.List;

import com.java_coffee.coffee_service.coffeeOrder.CoffeeOrderDto;
import com.java_coffee.coffee_service.userStub.UserStub;

public interface CartService {
    
    CartDto createCart(UserStub userStub);
    CartDto getCartById(long cartId);
    CartDto getCartByUserId(long userId);
    List<CartDto> getAllCarts();
    CartDto addItemToCart(long cartId, CoffeeOrderDto coffeeOrderDto);
    CartDto removeItemFromCart(long cartId, CoffeeOrderDto coffeeOrderDto);
    CartDto clearCart(long cartId);
    void deleteCartById(long cartId);

}
