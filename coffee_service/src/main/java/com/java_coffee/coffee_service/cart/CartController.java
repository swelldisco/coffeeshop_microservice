package com.java_coffee.coffee_service.cart;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.java_coffee.coffee_service.coffeeOrder.CoffeeOrderDto;
import com.java_coffee.coffee_service.userStub.UserStub;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping(value = "/api_v1/cart")
@AllArgsConstructor
public class CartController {

    @Autowired
    private CartService service;
    
    // http://127.0.0.1:8082/api_v1/cart/
    @PostMapping()
    public ResponseEntity<CartDto> createCart(@RequestBody UserStub userstub) {
        return new ResponseEntity<CartDto>(service.createCart(userstub), HttpStatus.CREATED);
    }

    // http://127.0.0.1:8082/api_v1/cart/cartId?id=1
    @GetMapping("/id")
    public ResponseEntity<CartDto> getCartById(@RequestParam(value = "id") long cartId){
        return new ResponseEntity<CartDto>(service.getCartById(cartId), HttpStatus.OK);
    }

    // http://127.0.0.1:8082/api_v1/cart/userId?id=1
    @GetMapping("/userId")
    public ResponseEntity<CartDto> getCartByUserId(@RequestParam(value = "id") long userId){
        return new ResponseEntity<CartDto>(service.getCartByUserId(userId), HttpStatus.OK);
    }

    // http://127.0.0.1:8082/api_v1/cart/all
    @GetMapping("/all")
    // only for admins for auditing purposes
    public ResponseEntity<List<CartDto>> getAllCarts() {
        return new ResponseEntity<>(service.getAllCarts(), HttpStatus.OK);
    }

    // http://127.0.0.1:8082/api_v1/cart/put?cartId=1
    @PutMapping("/put")
    public ResponseEntity<CartDto> addItemToCart(@RequestParam long cartId, CoffeeOrderDto coffeeOrderDto) {
        return new ResponseEntity<>(service.addItemToCart(cartId, coffeeOrderDto),HttpStatus.ACCEPTED);
    }

    // http://127.0.0.1:8082/api_v1/cart/remove?cartId=1
    @PutMapping("/remove")
    public ResponseEntity<CartDto> removeItemfFromCart(@RequestParam long cartId, CoffeeOrderDto coffeeOrderDto) {
        return new ResponseEntity<>(service.removeItemFromCart(cartId, coffeeOrderDto),HttpStatus.ACCEPTED);
    }

    // http://127.0.0.1:8082/api_v1/cart/clear?cartId=1
    @GetMapping("/clear")
    public ResponseEntity<CartDto> clearCart(@RequestParam long cartId) {
        return new ResponseEntity<>(service.clearCart(cartId),HttpStatus.ACCEPTED);
    }

    // http://127.0.0.1:8082/api_v1/cart/1
    @DeleteMapping("")
    // only for admins users
    public ResponseEntity<HttpStatus> deleteCartById(@PathVariable long cartId) {
        service.deleteCartById(cartId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
