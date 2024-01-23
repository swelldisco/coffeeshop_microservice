package com.java_coffee.coffee_service.coffee;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.java_coffee.coffee_service.menu.MenuService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api_vi/coffee")
@AllArgsConstructor
public class CoffeeController {
    
    @Autowired
    private MenuService menuService;

    // http://127.0.0.1/api_v1/coffee/loadMenu
    @GetMapping("/loadMenu")
    public ResponseEntity<HttpStatus> loadMenuItems() {
        menuService.loadMenu();
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    // http://127.0.0.1/api_v1/coffee/
    @PutMapping("")
    public ResponseEntity<HttpStatus> initializeMenu(CoffeeDto coffeeDto) {
        menuService.addCoffee(coffeeDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    // http://127.0.0.1/api_v1/coffee/getMenu
    @GetMapping("/getMenu")
    public ResponseEntity<List<CoffeeDto>> getMenu() {
        return new ResponseEntity<>(menuService.getMenu(), HttpStatus.OK);
    }

    // http://127.0.0.1/api_v1/coffee/coffee?name="latte"
    @GetMapping("/coffee")
    public ResponseEntity<List<CoffeeDto>> getCoffeeByName(@RequestParam String name) {
        return new ResponseEntity<>(menuService.getCoffeesByName(name), HttpStatus.OK);
    }
}
