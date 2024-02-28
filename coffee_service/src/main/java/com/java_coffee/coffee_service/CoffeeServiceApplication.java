package com.java_coffee.coffee_service;

// import java.util.Arrays;
// import java.util.List;

// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;
// import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

// import com.java_coffee.coffee_service.cart.Cart;
// import com.java_coffee.coffee_service.cart.CartRepository;
// import com.java_coffee.coffee_service.coffee.Coffee;
// import com.java_coffee.coffee_service.coffee.CoffeeRepository;
// import com.java_coffee.coffee_service.coffee.constants.CoffeeSize;
// import com.java_coffee.coffee_service.coffeeOrder.CoffeeOrder;
// import com.java_coffee.coffee_service.coffeeOrder.CoffeeOrderRepository;
// import com.java_coffee.coffee_service.pojo.UserStub;


@SpringBootApplication
@ComponentScan("com.java_coffee.coffee_service")
public class CoffeeServiceApplication implements CommandLineRunner{

	// @Autowired
	// private CoffeeRepository coffeeRepo;

    // @Autowired
    // private CartRepository cartRepo;

    // @Autowired
    // private CoffeeOrderRepository orderRepo;

    // private final Logger LOGGER = LoggerFactory.getLogger(CoffeeServiceApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(CoffeeServiceApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// final List<String> ing1 = Arrays.asList("Espresso", "Skim Milk");
        // final List<String> ing2 = Arrays.asList("Espresso", "Whole Milk", "Chocolate Syrup");
        // final List<String> ing3 = Arrays.asList("Espresso", "2% Milk");
        // final List<String> ing4 = Arrays.asList("Espresso", "Filtered Water");
        // final List<String> ing5 = Arrays.asList("Espresso");
        // final List<Coffee> tempList = Arrays.asList(
        //     new Coffee(0L, CoffeeSize.SHORT,"Mocha", 3.29, ing2),
        //     new Coffee(0L, CoffeeSize.TALL,"Mocha", 3.29, ing2),
        //     new Coffee(0L, CoffeeSize.GRANDE,"Mocha", 3.29, ing2),
        //     new Coffee(0L, CoffeeSize.VENTI,"Mocha", 3.29, ing2),

        //     new Coffee(0L, CoffeeSize.SHORT, "Skim Latte", 3.25, ing1),
        //     new Coffee(0L, CoffeeSize.TALL, "Skim Latte", 3.25, ing1),
        //     new Coffee(0L, CoffeeSize.GRANDE, "Skim Latte", 3.25, ing1),
        //     new Coffee(0L, CoffeeSize.VENTI, "Skim Latte", 3.25, ing1),

        //     new Coffee(0L, CoffeeSize.SHORT, "Latte", 3.25, ing3),
        //     new Coffee(0L, CoffeeSize.TALL, "Latte", 3.25, ing3),
        //     new Coffee(0L, CoffeeSize.GRANDE, "Latte", 3.25, ing3),
        //     new Coffee(0L, CoffeeSize.VENTI, "Latte", 3.25, ing3),

        //     new Coffee(0L, CoffeeSize.SHORT, "Macchiato", 3.95, ing4),
        //     new Coffee(0L, CoffeeSize.TALL, "Macchiato", 3.95, ing4),
        //     new Coffee(0L, CoffeeSize.GRANDE, "Macchiato", 3.95, ing4),
        //     new Coffee(0L, CoffeeSize.VENTI, "Macchiato", 3.95, ing4),

        //     new Coffee(0L, CoffeeSize.SHORT, "Cappuccino", 3.00, ing4),
        //     new Coffee(0L, CoffeeSize.TALL, "Cappuccino", 3.00, ing4),
        //     new Coffee(0L, CoffeeSize.GRANDE, "Cappuccino", 3.00, ing4),
        //     new Coffee(0L, CoffeeSize.VENTI, "Cappuccino", 3.00, ing4),

        //     new Coffee(0L, CoffeeSize.SHORT, "Americano", 2.50, ing5),
        //     new Coffee(0L, CoffeeSize.TALL, "Americano", 2.50, ing5),
        //     new Coffee(0L, CoffeeSize.GRANDE, "Americano", 2.50, ing5),
        //     new Coffee(0L, CoffeeSize.VENTI, "Americano", 2.50, ing5),

        //     new Coffee(0L, CoffeeSize.SHORT, "Flat White", 2.95, ing3),
        //     new Coffee(0L, CoffeeSize.TALL, "Flat White", 2.95, ing3),
        //     new Coffee(0L, CoffeeSize.GRANDE, "Flat White", 2.95, ing3),
        //     new Coffee(0L, CoffeeSize.VENTI, "Flat White", 2.95, ing3),

        //     new Coffee(0L, CoffeeSize.SHORT, "Red Eye", 4.50, ing5),
        //     new Coffee(0L, CoffeeSize.TALL, "Red Eye", 4.50, ing5),
        //     new Coffee(0L, CoffeeSize.GRANDE, "Red Eye", 4.50, ing5),
        //     new Coffee(0L, CoffeeSize.VENTI, "Red Eye", 4.50, ing5),

        //     new Coffee(0L, CoffeeSize.SHORT, "Espresso", 2.00, ing5),
        //     new Coffee(0L, CoffeeSize.TALL, "Espresso", 2.00, ing5),
        //     new Coffee(0L, CoffeeSize.GRANDE, "Espresso", 2.00, ing5),
        //     new Coffee(0L, CoffeeSize.VENTI, "Espresso", 2.00, ing5)
        // );
        
        // for (Coffee coffee : tempList) {
        //     coffeeRepo.save(coffee);
        // }

        
        // UserStub user1 = new UserStub(1L, "Billy_Bob", "b-b@gmail.com");
        // UserStub user2 = new UserStub(2L, "Jimbo", "jimbo@mail.ru");
        // UserStub user3 = new UserStub(3L, "Bubba", "bubsy@aol.com");
        // UserStub user4 = new UserStub(4L, "Cletus", "soccercleets@gmail.com");
        // UserStub user5 = new UserStub(5L, "Jethro", "jnotull@yahoo.com");
        
        // List<Cart> testCarts = Arrays.asList(
        //     new Cart(user1),
        //     new Cart(user2),
        //     new Cart(user3),
        //     new Cart(user4)
        // );

        // for (Cart cart : testCarts) {
        //     cartRepo.save(cart);
        // }

        // Cart tempCart1 = new Cart(cartRepo.findById(2L).get());

        // Coffee tempCoffee1 = new Coffee(coffeeRepo.findById(3L).get());
        // Coffee tempCoffee2 = new Coffee(coffeeRepo.findById(7L).get());
        // Coffee tempCoffee3 = new Coffee(coffeeRepo.findById(11L).get());

        // orderRepo.save(new CoffeeOrder(tempCoffee1, tempCart1, 2));
        // orderRepo.save(new CoffeeOrder(tempCoffee2, tempCart1, 1));
        // orderRepo.save(new CoffeeOrder(tempCoffee3, tempCart1, 1));

	}


}
