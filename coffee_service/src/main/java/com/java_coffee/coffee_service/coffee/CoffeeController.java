package com.java_coffee.coffee_service.coffee;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping(value = "/api_v1/coffee")
@AllArgsConstructor
public class CoffeeController {
    
    @Autowired
    private CoffeeService service;

    // http://127.0.0.1:8082/api_v1/coffee/
    @PostMapping()
    // barista and admin roles
    public ResponseEntity<CoffeeDto> createCoffee(@Valid CoffeeDto coffeeDto) {
        return new ResponseEntity<>(service.createCoffee(coffeeDto), HttpStatus.CREATED);
    }

    // http://127.0.0.1:8082/api_v1/coffee/initializeMenu
    @GetMapping("/initializeMenu")
    // only for admins for bring up the application with a menu of coffees
    public ResponseEntity<HttpStatus> initializeMenu() {
        service.initializeMenu();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // http://127.0.0.1:8082/api_v1/coffee/id?coffeeId=1
    @GetMapping("/id/{coffeeId}")
    public ResponseEntity<CoffeeDto> getCoffeeById(@PathVariable long coffeeId) {
        return new ResponseEntity<>(service.findCoffeeById(coffeeId), HttpStatus.OK);
    }

    // http://127.0.0.1:8082/api_v1/coffee/all
    @GetMapping("/all")
    public ResponseEntity<List<CoffeeDto>> getAllCoffee() {
        return new ResponseEntity<>(service.findAllCoffees(), HttpStatus.OK);
    }

    // http://127.0.0.1:8082/api_v1/coffee/byName?drinkName=latte
    @GetMapping("/byName/{drinkName}")
    public ResponseEntity<List<CoffeeDto>> getAllCoffeesByName(@PathVariable String drinkName) {
        return new ResponseEntity<>(service.findAllByName(drinkName), HttpStatus.OK);
    }

    // http://127.0.0.1:8082/api_v1/coffee/update?coffeeId=1
    @PutMapping("/update/{coffeeId}")
    // also only for admin and barista roles
    public ResponseEntity<CoffeeDto> updateCoffee(@PathVariable long coffeeId, @Valid CoffeeDto coffeeDto) {
        return new ResponseEntity<>(service.updateCoffee(coffeeId, coffeeDto), HttpStatus.ACCEPTED);
    }

    // http://127.0.0.1:8082/api_v1/coffee/delete?coffeeId=1
    @DeleteMapping("/delete/{coffeeId}")
    // and again, only for admin and baristas
    public ResponseEntity<HttpStatus> deleteCoffee(@PathVariable long coffeeId) {
        service.deleteCoffeeById(coffeeId);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    
    
}
