package com.java_coffee.coffee_service;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import com.java_coffee.coffee_service.coffee.Coffee;
import com.java_coffee.coffee_service.coffee.CoffeeRepository;
import com.java_coffee.coffee_service.coffee.constants.CoffeeSize;


@SpringBootApplication
@ComponentScan("com.java_coffee.coffee_service")
public class CoffeeServiceApplication implements CommandLineRunner{

	@Autowired
	private CoffeeRepository repo;

	public static void main(String[] args) {
		SpringApplication.run(CoffeeServiceApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		final List<String> ing1 = Arrays.asList("Espresso", "Skim Milk");
        final List<String> ing2 = Arrays.asList("Espresso", "Whole Milk", "Chocolate Syrup");
        final List<String> ing3 = Arrays.asList("Espresso", "2% Milk");
        final List<String> ing4 = Arrays.asList("Espresso", "Filtered Water");
        final List<String> ing5 = Arrays.asList("Espresso");
        final List<Coffee> tempList = Arrays.asList(
            new Coffee(0L, CoffeeSize.SHORT,"Mocha", 3.29, ing2),
            new Coffee(0L, CoffeeSize.TALL,"Mocha", 3.29, ing2),
            new Coffee(0L, CoffeeSize.GRANDE,"Mocha", 3.29, ing2),
            new Coffee(0L, CoffeeSize.VENTI,"Mocha", 3.29, ing2),

            new Coffee(0L, CoffeeSize.SHORT, "Skim Latte", 3.25, ing1),
            new Coffee(0L, CoffeeSize.TALL, "Skim Latte", 3.25, ing1),
            new Coffee(0L, CoffeeSize.GRANDE, "Skim Latte", 3.25, ing1),
            new Coffee(0L, CoffeeSize.VENTI, "Skim Latte", 3.25, ing1),

            new Coffee(0L, CoffeeSize.SHORT, "Latte", 3.25, ing3),
            new Coffee(0L, CoffeeSize.TALL, "Latte", 3.25, ing3),
            new Coffee(0L, CoffeeSize.GRANDE, "Latte", 3.25, ing3),
            new Coffee(0L, CoffeeSize.VENTI, "Latte", 3.25, ing3),

            new Coffee(0L, CoffeeSize.SHORT, "Macchiato", 3.95, ing4),
            new Coffee(0L, CoffeeSize.TALL, "Macchiato", 3.95, ing4),
            new Coffee(0L, CoffeeSize.GRANDE, "Macchiato", 3.95, ing4),
            new Coffee(0L, CoffeeSize.VENTI, "Macchiato", 3.95, ing4),

            new Coffee(0L, CoffeeSize.SHORT, "Cappuccino", 3.00, ing4),
            new Coffee(0L, CoffeeSize.TALL, "Cappuccino", 3.00, ing4),
            new Coffee(0L, CoffeeSize.GRANDE, "Cappuccino", 3.00, ing4),
            new Coffee(0L, CoffeeSize.VENTI, "Cappuccino", 3.00, ing4),

            new Coffee(0L, CoffeeSize.SHORT, "Americano", 2.50, ing5),
            new Coffee(0L, CoffeeSize.TALL, "Americano", 2.50, ing5),
            new Coffee(0L, CoffeeSize.GRANDE, "Americano", 2.50, ing5),
            new Coffee(0L, CoffeeSize.VENTI, "Americano", 2.50, ing5),

            new Coffee(0L, CoffeeSize.SHORT, "Flat White", 2.95, ing3),
            new Coffee(0L, CoffeeSize.TALL, "Flat White", 2.95, ing3),
            new Coffee(0L, CoffeeSize.GRANDE, "Flat White", 2.95, ing3),
            new Coffee(0L, CoffeeSize.VENTI, "Flat White", 2.95, ing3),

            new Coffee(0L, CoffeeSize.SHORT, "Red Eye", 4.50, ing5),
            new Coffee(0L, CoffeeSize.TALL, "Red Eye", 4.50, ing5),
            new Coffee(0L, CoffeeSize.GRANDE, "Red Eye", 4.50, ing5),
            new Coffee(0L, CoffeeSize.VENTI, "Red Eye", 4.50, ing5),

            new Coffee(0L, CoffeeSize.SHORT, "Espresso", 2.00, ing5),
            new Coffee(0L, CoffeeSize.TALL, "Espresso", 2.00, ing5),
            new Coffee(0L, CoffeeSize.GRANDE, "Espresso", 2.00, ing5),
            new Coffee(0L, CoffeeSize.VENTI, "Espresso", 2.00, ing5)
        );
        
        for (Coffee coffee : tempList) {
            repo.save(coffee);
        }
        
	}


}
