package com.java_coffee.coffee_service.controllerTests;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Component;

import com.java_coffee.coffee_service.coffee.Coffee;
import com.java_coffee.coffee_service.coffee.constants.CoffeeSize;

// right now this is just a copy of MenuItems.java.  Eventually I want MenuItems to be able to pull from external files, but will still need to be able to use an isolated list of coffees for testing.
@Component
public class TestMenuItems {

    public List<Coffee> createLists() {
        final List<String> ing1 = Arrays.asList("Espresso", "Skim Milk");
        final List<String> ing2 = Arrays.asList("Espresso", "Whole Milk", "Chocolate Syrup");
        final List<String> ing3 = Arrays.asList("Espresso", "2% Milk");
        final List<String> ing4 = Arrays.asList("Espresso", "Filtered Water");
        final List<String> ing5 = Arrays.asList("Espresso");

        final List<Coffee> tempList = Arrays.asList(
            new Coffee(0L, CoffeeSize.SHORT, "Mocha", 3.29, ing2),
            new Coffee(1L, CoffeeSize.TALL, "Mocha", 3.29, ing2),
            new Coffee(2L, CoffeeSize.GRANDE, "Mocha", 3.29, ing2),
            new Coffee(3L, CoffeeSize.VENTI, "Mocha", 3.29, ing2),

            new Coffee(4L, CoffeeSize.SHORT, "Skim Latte", 3.25, ing1),
            new Coffee(5L, CoffeeSize.TALL, "Skim Latte", 3.25, ing1),
            new Coffee(6L, CoffeeSize.GRANDE, "Skim Latte", 3.25, ing1),
            new Coffee(7L, CoffeeSize.VENTI, "Skim Latte", 3.25, ing1),

            new Coffee(8L, CoffeeSize.SHORT, "Latte", 3.25, ing3),
            new Coffee(9L, CoffeeSize.TALL, "Latte", 3.25, ing3),
            new Coffee(10L, CoffeeSize.GRANDE, "Latte", 3.25, ing3),
            new Coffee(11L, CoffeeSize.VENTI, "Latte", 3.25, ing3),

            new Coffee(12L, CoffeeSize.SHORT, "Macchiato", 3.95, ing4),
            new Coffee(13L, CoffeeSize.TALL, "Macchiato", 3.95, ing4),
            new Coffee(14L, CoffeeSize.GRANDE, "Macchiato", 3.95, ing4),
            new Coffee(15L, CoffeeSize.VENTI, "Macchiato", 3.95, ing4),

            new Coffee(16L, CoffeeSize.SHORT, "Cappuccino", 3.00, ing4),
            new Coffee(17L, CoffeeSize.TALL, "Cappuccino", 3.00, ing4),
            new Coffee(18L, CoffeeSize.GRANDE, "Cappuccino", 3.00, ing4),
            new Coffee(19L, CoffeeSize.VENTI, "Cappuccino", 3.00, ing4),

            new Coffee(20L, CoffeeSize.SHORT, "Americano", 2.50, ing5),
            new Coffee(21L, CoffeeSize.TALL, "Americano", 2.50, ing5),
            new Coffee(22L, CoffeeSize.GRANDE, "Americano", 2.50, ing5),
            new Coffee(23L, CoffeeSize.VENTI, "Americano", 2.50, ing5),

            new Coffee(24L, CoffeeSize.SHORT, "Flat White", 2.95, ing3),
            new Coffee(25L, CoffeeSize.TALL, "Flat White", 2.95, ing3),
            new Coffee(26L, CoffeeSize.GRANDE, "Flat White", 2.95, ing3),
            new Coffee(27L, CoffeeSize.VENTI, "Flat White", 2.95, ing3),

            new Coffee(28L, CoffeeSize.SHORT, "Red Eye", 4.50, ing5),
            new Coffee(29L, CoffeeSize.TALL, "Red Eye", 4.50, ing5),
            new Coffee(30L, CoffeeSize.GRANDE, "Red Eye", 4.50, ing5),
            new Coffee(31L, CoffeeSize.VENTI, "Red Eye", 4.50, ing5),

            new Coffee(32L, CoffeeSize.SHORT, "Espresso", 2.00, ing5),
            new Coffee(33L, CoffeeSize.TALL, "Espresso", 2.00, ing5),
            new Coffee(34L, CoffeeSize.GRANDE, "Espresso", 2.00, ing5),
            new Coffee(35L, CoffeeSize.VENTI, "Espresso", 2.00, ing5)
        );
        return tempList;
    }

}
