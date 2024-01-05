package com.java_coffee.coffee_service.coffee;

import java.util.Comparator;

public class CoffeeComparator implements Comparator<Coffee>{

    @Override
    public int compare(Coffee coffee1, Coffee coffee2) {
        int coffeeComparison = coffee1.getDrinkName().compareToIgnoreCase(coffee2.getDrinkName());
        if (coffeeComparison != 0) {
            return coffeeComparison;
        } else {
            return coffee1.getSize().compareTo(coffee2.getSize());
        }
    }
    
}
