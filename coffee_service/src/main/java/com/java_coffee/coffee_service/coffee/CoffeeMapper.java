package com.java_coffee.coffee_service.coffee;

public class CoffeeMapper {
    public Coffee mapToCoffee(CoffeeDto source) {
        return new Coffee(
            source.coffeeId(),
            source.size(),
            source.drinkName(),
            source.basePrice(),
            source.ingredientList()
        );
    }

    public CoffeeDto mapToDto(Coffee source) {
         return new CoffeeDto(
            source.getCoffeeId(),
            source.getSize(),
            source.getDrinkName(),
            source.getBasePrice(),
            source.getPrice(),
            source.getIngredientList()
        );
    }
}
