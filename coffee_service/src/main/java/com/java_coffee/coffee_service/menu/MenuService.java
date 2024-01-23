package com.java_coffee.coffee_service.menu;

import java.util.List;

import com.java_coffee.coffee_service.coffee.CoffeeDto;

public interface MenuService {
    
    void loadMenu();
    void addCoffee(CoffeeDto coffeeDto);
    CoffeeDto getMenuCoffeeById(int coffeeId);
    void updateCoffee(int coffeeId, CoffeeDto coffeeDto);
    void removeCoffee(int coffeeId);
    List<CoffeeDto> getMenu();
    List<CoffeeDto> getCoffeesByName(String name);

}
