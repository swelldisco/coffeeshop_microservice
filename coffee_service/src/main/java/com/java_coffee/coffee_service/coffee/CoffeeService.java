package com.java_coffee.coffee_service.coffee;

import java.util.List;

public interface CoffeeService {
    
    CoffeeDto createCoffee(CoffeeDto coffeeDto);
    CoffeeDto findCoffeeById(long coffeeId);
    CoffeeDto findCoffeeByNameAndSize(String drinkName, String size);
    List<CoffeeDto> findAllCoffees();
    List<CoffeeDto> findAllByName(String name);
    CoffeeDto updateCoffee(long coffeeId, CoffeeDto coffeeDto);
    void deleteCoffeeById(long coffeeId);

    void initializeMenu();
    List<MenuItemDto> getMenu();
}
