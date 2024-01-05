package com.java_coffee.coffee_service.coffee;

import java.util.Arrays;
import java.util.List;

public class MenuItems {

    // a temporary method to load the menu items into the repository since this program has no persistence
    public void loadMenuItems(CoffeeRepository repo) {
        final List<String> ing1 = Arrays.asList("Espresso", "Skim Milk");
        final List<String> ing2 = Arrays.asList("Espresso", "Whole Milk", "Chocolate Syrup");
        final List<String> ing3 = Arrays.asList("Espresso", "2% Milk");
        final List<String> ing4 = Arrays.asList("Espresso", "Filtered Water");
        final List<String> ing5 = Arrays.asList("Espresso");
        final List<Coffee> tempList = Arrays.asList(
            new Coffee("Mocha", 2.50, ing2),
            new Coffee("Skim Latte", 2.50, ing1),
            new Coffee("Latte", 2.50, ing3),
            new Coffee("Macchiato", 2.50, ing4),
            new Coffee("Cappuccino", 2.50, ing4),
            new Coffee("Americano", 2.50, ing5),
            new Coffee("Flat White", 2.50, ing3),
            new Coffee("Red Eye", 2.50, ing5),
            new Coffee("Espresso", 2.50, ing5)
        );
        for (Coffee coffee : tempList) {
            repo.save(coffee);
        }
    }
}
