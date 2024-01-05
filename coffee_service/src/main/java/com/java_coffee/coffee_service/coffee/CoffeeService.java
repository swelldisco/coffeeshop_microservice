package com.java_coffee.coffee_service.coffee;

import java.util.List;

public interface CoffeeService {
    
    CoffeeDto createCoffee(CoffeeDto coffeeDto);
    CoffeeDto getCoffeeById(int coffeeId);
    List<CoffeeDto> getCoffeesByName(String name);
    List<CoffeeDto> getAllCoffees();
    CoffeeDto updateCoffee(int coffeeId, CoffeeDto coffeeDto);
    void deleteCoffee(int coffeeId);

    // void orderCoffee(Order coffeeOrder, CoffeeDto coffeeDto);

}
