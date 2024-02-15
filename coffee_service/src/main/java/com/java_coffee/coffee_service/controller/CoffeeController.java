package com.java_coffee.coffee_service.controller;

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

import com.java_coffee.coffee_service.cart.CartDto;
import com.java_coffee.coffee_service.cart.CartService;
import com.java_coffee.coffee_service.coffee.CoffeeDto;
import com.java_coffee.coffee_service.coffee.CoffeeService;
import com.java_coffee.coffee_service.coffee.MenuItemDto;
import com.java_coffee.coffee_service.coffeeOrder.CoffeeOrder;
import com.java_coffee.coffee_service.coffeeOrder.CoffeeOrderDto;
import com.java_coffee.coffee_service.coffeeOrder.CoffeeOrderService;
import com.java_coffee.coffee_service.pojo.OrderReceipt;
import com.java_coffee.coffee_service.pojo.UserStub;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping(value = "/api_v1/coffees")
@AllArgsConstructor
public class CoffeeController {
    
    @Autowired
    private CoffeeService coffeeService;

    @Autowired
    private CartService cartService;

    @Autowired
    private CoffeeOrderService orderService;

    // coffee REST APIs
    // http://127.0.0.1:8082/api_v1/coffees/createCoffee
    @PostMapping("/createCoffee")
    // barista and admin roles
    public ResponseEntity<CoffeeDto> createCoffee(@RequestBody @Valid CoffeeDto coffeeDto) {
        return new ResponseEntity<>(coffeeService.createCoffee(coffeeDto), HttpStatus.CREATED);
    }

    // http://127.0.0.1:8082/api_v1/coffees/initializeMenu
    @GetMapping("/initializeMenu")
    // only for admins for bring up the application with a menu of coffees
    public ResponseEntity<HttpStatus> initializeMenu() {
        coffeeService.initializeMenu();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // http://127.0.0.1:8082/api_v1/coffees/getMenu
    @GetMapping("/getMenu")
    public ResponseEntity<List<MenuItemDto>> getMenu() {
        return new ResponseEntity<>(coffeeService.getMenu(), HttpStatus.OK);
    }

    // http://127.0.0.1:8082/api_v1/coffees/coffeeId?id=1
    @GetMapping("/coffeeId")
    public ResponseEntity<CoffeeDto> getCoffeeById(@RequestParam(name = "id") long coffeeId) {
        return new ResponseEntity<>(coffeeService.findCoffeeById(coffeeId), HttpStatus.OK);
    }

    // http://127.0.0.1:8082/api_v1/coffees/coffee?name=latte&size=grande
    @GetMapping("/coffee")
    public ResponseEntity<CoffeeDto> getCoffeeByNameAndSize(@RequestParam(name = "name") String drinkName, @RequestParam String size) {
        return new ResponseEntity<>(coffeeService.findCoffeeByNameAndSize(drinkName, size), HttpStatus.OK);
    }

    // http://127.0.0.1:8082/api_v1/coffees/allCoffees
    @GetMapping("/allCoffees")
    public ResponseEntity<List<CoffeeDto>> getAllCoffee() {
        return new ResponseEntity<>(coffeeService.findAllCoffees(), HttpStatus.OK);
    }

    // http://127.0.0.1:8082/api_v1/coffees/drinkName?name=latte
    @GetMapping("/drinkName")
    public ResponseEntity<List<CoffeeDto>> getCoffeesByName(@RequestParam(name = "name") String drinkName) {
        return new ResponseEntity<>(coffeeService.findAllByName(drinkName), HttpStatus.OK);
    }

    // http://127.0.0.1:8082/api_v1/coffees/updateCoffee/1
    @PutMapping("/updateCoffee/{coffeeId}")
    // also only for admin and barista roles
    public ResponseEntity<CoffeeDto> updateCoffee(@PathVariable long coffeeId, @RequestBody @Valid CoffeeDto coffeeDto) {
        return new ResponseEntity<>(coffeeService.updateCoffee(coffeeId, coffeeDto), HttpStatus.ACCEPTED);
    }

    // http://127.0.0.1:8082/api_v1/coffees/deleteCoffee/1
    @DeleteMapping("/deleteCoffee/{coffeeId}")
    // and again, only for admin and baristas
    public ResponseEntity<HttpStatus> deleteCoffee(@PathVariable long coffeeId) {
        coffeeService.deleteCoffeeById(coffeeId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Cart REST APIs
    // http://127.0.0.1:8082/api_v1/coffees/createCart
    @PostMapping("/createCart")
    public ResponseEntity<CartDto> createCart(@RequestBody UserStub userstub) {
        return new ResponseEntity<CartDto>(cartService.createCart(userstub), HttpStatus.CREATED);
    }

    // this should likely be restricted to just the cart's owner, baristas and admins
    // http://127.0.0.1:8082/api_v1/coffees/cartById?id=1
    @GetMapping("/cartById")
    public ResponseEntity<CartDto> getCartById(@RequestParam(name = "id") long cartId){
        return new ResponseEntity<CartDto>(cartService.getCartById(cartId), HttpStatus.OK);
    }

    // also should only be viewable by the cart's owner, baritas and admins
    // http://127.0.0.1:8082/api_v1/coffees/cartByUserId?id=1
    @GetMapping("/cartByUserId")
    public ResponseEntity<CartDto> getCartByUserId(@RequestParam(name = "id") long userId){
        return new ResponseEntity<CartDto>(cartService.getCartByUserId(userId), HttpStatus.OK);
    }

    // probably just admins for auditing and troubleshooting
    // http://127.0.0.1:8082/api_v1/coffees/allCarts
    @GetMapping("/allCarts")
    public ResponseEntity<List<CartDto>> getAllCarts() {
        return new ResponseEntity<>(cartService.getAllCarts(), HttpStatus.OK);
    }

    // http://127.0.0.1:8082/api_v1/coffees/clearCart?cartId=1
    @GetMapping("/clearCart")
    public ResponseEntity<CartDto> clearCart(@RequestParam long cartId) {
        return new ResponseEntity<>(cartService.clearCart(cartId),HttpStatus.ACCEPTED);
    }

    // http://127.0.0.1:8082/api_v1/coffees/deleteCart/1
    @DeleteMapping("/deleteCart/{cartId}")
    // only for admins users
    public ResponseEntity<HttpStatus> deleteCartById(@PathVariable long cartId) {
        cartService.deleteCartById(cartId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // coffee order REST APIs
    // http://127.0.0.1:8082/api_v1/coffees/createOrder
    @PostMapping("/createOrder")
    public ResponseEntity<CoffeeOrderDto> createCoffeeOrder(@RequestBody @Valid CoffeeOrderDto coffeeOrderDto) {
        return new ResponseEntity<>(orderService.createOrder(coffeeOrderDto), HttpStatus.CREATED);
    }

    // this should probably be admin only, since neither users nor baristas should ever need this
    // http://127.0.0.1:8082/api_v1/coffees/allOrders
    @GetMapping("/allOrders")
    public ResponseEntity<List<CoffeeOrderDto>> getAllCoffeeOrders() {
        return new ResponseEntity<>(orderService.getAllOrders(), HttpStatus.OK);
    }

    // should only be used by cart owner, admin and baristas
    // http://127.0.0.1:8082/api_v1/coffees/orderById?orderId=1
    @GetMapping("/orderById")
    public ResponseEntity<CoffeeOrderDto> getOrderById(@RequestParam long orderId) {
        return new ResponseEntity<>(orderService.getOrderByOrderId(orderId), HttpStatus.OK);
    }

    // http://127.0.0.1:8082/api_v1/coffees/orderByCartId?id=1
    @GetMapping("/orderByCartId")
    public ResponseEntity<List<CoffeeOrderDto>> getAllOrdersByCart(@RequestParam(name = "id") long cartId) {
        return new ResponseEntity<>(orderService.getOrderByCartId(cartId), HttpStatus.OK);
    }

    // http://127.0.0.1:8082/api_v1/coffees/orderByUserId?id=1
    @GetMapping("/orderByUserId")
    public ResponseEntity<List<CoffeeOrderDto>> getAllOrdersByUser(@RequestParam(name ="id") long userId) {
        return new ResponseEntity<>(orderService.getOrderByUserId(userId), HttpStatus.OK);
    }

    // http://127.0.0.1:8082/api_v1/coffees/updateOrder/1
    @PutMapping("/updateOrder/{orderId}")
    public ResponseEntity<CoffeeOrderDto> updateOrder(@RequestBody @Valid CoffeeOrderDto coffeeOrderDto, @PathVariable long orderId) {
        return new ResponseEntity<>(orderService.updateOrder(orderId, coffeeOrderDto), HttpStatus.ACCEPTED);
    }

    // http://127.0.0.1:8082/api_v1/coffees/deleteOrder/1
    @DeleteMapping("/deleteOrder/{orderId}")
    public ResponseEntity<CoffeeOrder> deleteOrder(@PathVariable long orderId) {
        orderService.deleteOrderById(orderId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // http://127.0.0.1:8082/api_v1/coffees/orderTotal?id=2
    @GetMapping("/orderTotal")
    public ResponseEntity<Double> getOrderTotal(@RequestParam(name = "id") long cartId) {
        return new ResponseEntity<>(orderService.orderTotal(cartId), HttpStatus.OK);
    }

    // http://127.0.0.1:8082/api_v1/coffees/receipt?cartId=2
    @GetMapping("/receipt")
    public ResponseEntity<OrderReceipt> generateReceipt(@RequestBody UserStub userStub, @RequestParam long cartId) {
        return new ResponseEntity<>(orderService.generateReceipt(userStub, cartId), HttpStatus.OK);
    }
    
    
}
