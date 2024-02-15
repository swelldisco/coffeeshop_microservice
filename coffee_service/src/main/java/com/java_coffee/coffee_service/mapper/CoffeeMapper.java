package com.java_coffee.coffee_service.mapper;

import org.springframework.stereotype.Component;

import com.java_coffee.coffee_service.cart.Cart;
import com.java_coffee.coffee_service.cart.CartDto;
import com.java_coffee.coffee_service.coffee.Coffee;
import com.java_coffee.coffee_service.coffee.CoffeeDto;
import com.java_coffee.coffee_service.coffeeOrder.CoffeeOrder;
import com.java_coffee.coffee_service.coffeeOrder.CoffeeOrderDto;

@Component
public class CoffeeMapper {

    public Coffee mapToCoffee(CoffeeDto source) {
        return new Coffee(
            source.coffeeId(),
            source.coffeeSize(),
            source.drinkName(),
            source.basePrice(),
            source.ingredientList()
        );
    }

    public CoffeeDto mapToCoffeeDto(Coffee source) {
        return new CoffeeDto(
            source.getCoffeeId(), 
            source.getSize(),  
            source.getDrinkName(), 
            source.getBasePrice(), 
            source.getPrice(),
            source.getIngredientList()
        );
    }

    public CoffeeOrder mapToCoffeeOrder(CoffeeOrderDto source) {
        return new CoffeeOrder(
            source.orderId(),
            mapToCoffee(source.coffeeDto()),
            source.cartId(),
            source.userId(),
            source.quantity(),
            source.lineItemTotal(),
            source.timeStamp(),
            source.isPaid()
        );
    }

    public CoffeeOrderDto mapToCoffeeOrderDto(CoffeeOrder source) {
        return new CoffeeOrderDto(source.getOrderId(), 
            mapToCoffeeDto(source.getCoffee()),
            source.getCartId(), 
            source.getUserId(),
            source.getQuantity(),
            source.getLineItemTotal(), 
            source.getTimeStamp(),
            source.getIsPaid()
        );
    }

    public Cart mapToCart(CartDto source){
        return new Cart(
            source.cartId(),
            source.userId(),
            source.orders().stream()
                .map(o -> mapToCoffeeOrder(o))
                .toList()
        );
    }

    public CartDto mapToCartDto(Cart source) {
        return new CartDto(
            source.getCartId(), 
            source.getUserId(), 
            source.getOrders().stream()
                .map(o -> mapToCoffeeOrderDto(o))
                .toList()
        );
    }
    
}
