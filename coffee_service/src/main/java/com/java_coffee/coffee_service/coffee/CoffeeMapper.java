package com.java_coffee.coffee_service.coffee;

public class CoffeeMapper {
    public Coffee mapToCofee(CoffeeDto source) {
        return new Coffee(
            source.getId(),
            source.getSize(),
            source.getDrinkName(),
            source.getBasePrice(),
            source.getIngredientsList()
        );
    }

    public CoffeeDto mapToDto(Coffee source) {
         return new CoffeeDto(
            source.getId(),
            source.getSize(),
            source.getDrinkName(),
            source.getBasePrice(),
            source.getIngredientsList()
        );
    }
}
