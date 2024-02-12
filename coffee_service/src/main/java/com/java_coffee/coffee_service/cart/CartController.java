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

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping(value = "/api_v1/cart")
@AllArgsConstructor
public class CartController {

    @Autowired
    private CartService service;
    
    // http://127.0.0.1:8082/api_v1/cart
    @PostMapping("")
    public ResponseEntity<CartDto> createCart(@RequestBody UserStub userstub) {
        return new ResponseEntity<CartDto>(service.createCart(userstub), HttpStatus.CREATED);
    }

    // this should likely be restricted to just the cart's owner, baristas and admins
    // http://127.0.0.1:8082/api_v1/cart/cartId?id=1
    @GetMapping("/cartId")
    public ResponseEntity<CartDto> getCartById(@RequestParam(name = "id") long cartId){
        return new ResponseEntity<CartDto>(service.getCartById(cartId), HttpStatus.OK);
    }

    // also should only be viewable by the cart's owner, baritas and admins
    // http://127.0.0.1:8082/api_v1/cart/userId?id=1
    @GetMapping("/userId")
    public ResponseEntity<CartDto> getCartByUserId(@RequestParam(name = "id") long userId){
        return new ResponseEntity<CartDto>(service.getCartByUserId(userId), HttpStatus.OK);
    }

    // probably just admins for auditing and troubleshooting
    // http://127.0.0.1:8082/api_v1/cart/all
    @GetMapping("/all")
    // only for admins for auditing purposes
    public ResponseEntity<List<CartDto>> getAllCarts() {
        return new ResponseEntity<>(service.getAllCarts(), HttpStatus.OK);
    }

    // cart owner only.. and may not even be needed thanks to how the DB works superceeded by the order creation api
    // http://127.0.0.1:8082/api_v1/cart/put?cartId=1
    @PutMapping("/put")
    public ResponseEntity<CartDto> addItemToCart(@RequestParam long cartId, @RequestBody @Valid CoffeeOrderDto coffeeOrderDto) {
        return new ResponseEntity<>(service.addItemToCart(cartId, coffeeOrderDto),HttpStatus.ACCEPTED);
    }

    // probably don't need this too, also superceeded by the order deletion api
    // http://127.0.0.1:8082/api_v1/cart/remove?cartId=1
    @PutMapping("/remove")
    public ResponseEntity<CartDto> removeItemfFromCart(@RequestParam long cartId, @RequestBody @Valid CoffeeOrderDto coffeeOrderDto) {
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
