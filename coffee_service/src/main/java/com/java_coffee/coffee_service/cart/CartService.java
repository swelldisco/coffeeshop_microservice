package com.java_coffee.coffee_service.cart;

import java.util.List;

import com.java_coffee.coffee_service.pojo.UserStub;

public interface CartService {
    
    CartDto createCart(UserStub userStub);
    CartDto getCartById(long cartId);
    CartDto getCartByUserId(long userId);
    List<CartDto> getAllCarts();
    CartDto clearCart(long cartId);
    void deleteCartById(long cartId);

}
