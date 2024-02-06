package com.java_coffee.coffee_service.coffeeOrder;

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

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api_v1/order")
@AllArgsConstructor
public class CoffeeOrderController {

    
    @Autowired
    private CoffeeOrderService service;

    // http://127.0.0.1:8082/api_v1/order/
    @PostMapping()
    public ResponseEntity<CoffeeOrderDto> createCoffeeOrder(@RequestBody @Valid CoffeeOrderDto coffeeOrderDto) {
        return new ResponseEntity<>(service.createOrder(coffeeOrderDto), HttpStatus.CREATED);
    }

    // this should probably be admin only, since neither users nor baristas should ever need this
    // http://127.0.0.1:8082/api_v1/order/all
    @GetMapping("/all")
    public ResponseEntity<List<CoffeeOrderDto>> getAllCoffeeOrders() {
        return new ResponseEntity<>(service.getAllOrders(), HttpStatus.OK);
    }

    // http://127.0.0.1:8082/api_v1/order/order?orderId=1
    @GetMapping("/order")
    public ResponseEntity<CoffeeOrderDto> getOrderById(@RequestParam long orderId) {
        return new ResponseEntity<>(service.getOrderByOrderId(orderId), HttpStatus.OK);
    }

    // http://127.0.0.1:8082/api_v1/order/cart?cartId=1
    @GetMapping("/cart")
    public ResponseEntity<List<CoffeeOrderDto>> getAllOrdersByCart(@RequestParam long cartId) {
        return new ResponseEntity<>(service.getOrderByCartId(cartId), HttpStatus.OK);
    }

    // http://127.0.0.1:8082/api_v1/order/user?userId=1
    @GetMapping("/user")
    public ResponseEntity<List<CoffeeOrderDto>> getAllOrdersByUser(@RequestParam long userId) {
        return new ResponseEntity<>(service.getOrderByUserId(userId), HttpStatus.OK);
    }

    // http://127.0.0.1:8082/api_v1/order/1
    @PutMapping("/{orderId}")
    public ResponseEntity<CoffeeOrderDto> updateOrder(@RequestBody @Valid CoffeeOrderDto coffeeOrderDto, @PathVariable long orderId) {
        return new ResponseEntity<>(service.updateOrder(orderId, coffeeOrderDto), HttpStatus.ACCEPTED);
    }

    // http://127.0.0.1:8082/api_v1/order/1
    @DeleteMapping("/{orderId}")
    public ResponseEntity<CoffeeOrder> deleteOrder(@PathVariable long orderId) {
        service.deleteOrderById(orderId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    
}
