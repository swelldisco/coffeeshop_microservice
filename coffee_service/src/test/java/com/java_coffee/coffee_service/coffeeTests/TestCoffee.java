package com.java_coffee.coffee_service.coffeeTests;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.java_coffee.coffee_service.coffee.Coffee;
import com.java_coffee.coffee_service.coffee.constants.CoffeeSize;

public class TestCoffee {
    Coffee testCoffee;

    @BeforeEach
    public void setUp() {
        List<String> ingredients = new ArrayList<>();
        ingredients.add("Milk");
        ingredients.add("Sugar");
        ingredients.add("Caramel Syrup");
        ingredients.add("Chocolate Syrup");
        ingredients.add("Espresso");

        testCoffee = new Coffee(0L, CoffeeSize.SHORT, "Caramel Mocha", 3.25, ingredients);
    }

    @AfterEach
    public void tearDown() {
        testCoffee = null;
    }

    @Test
    public void testCalculateUpcharge() {
        Coffee testShort = new Coffee(0L, CoffeeSize.SHORT, "Short Test", 3.25, null);
        Assertions.assertNotNull(testShort.getPrice());
        Coffee testTall = new Coffee(0L, CoffeeSize.TALL, "Short Test", 3.25, null);
        Assertions.assertNotNull(testTall.getPrice());
        Coffee testGrande = new Coffee(0L, CoffeeSize.GRANDE, "Short Test", 3.25, null);
        Assertions.assertNotNull(testGrande.getPrice());
        Coffee testVenti = new Coffee(0L, CoffeeSize.VENTI, "Short Test", 3.25, null);
        Assertions.assertNotNull(testVenti.getPrice());

        double basePrice = testGrande.getBasePrice();
        double shortPrice = testShort.getPrice();
        double tallPrice = testTall.getPrice();
        double grandePrice = testGrande.getPrice();
        double ventiPrice = testVenti.getPrice();

        Assertions.assertNotNull(testShort.getBasePrice());
        Assertions.assertEquals(basePrice, shortPrice);
        Assertions.assertEquals((basePrice + .30), tallPrice);
        Assertions.assertEquals((basePrice + .65), grandePrice);
        Assertions.assertEquals((basePrice + 1.00), ventiPrice);

        testVenti.setSize(CoffeeSize.SHORT);
        testVenti.setPrice();

        Assertions.assertEquals(testVenti.getPrice(), testVenti.getBasePrice()); 
    }

    @Test
    public void testReadOutIngredientList() {
        Coffee testCoffeeNoIngredients = new Coffee(0L, CoffeeSize.SHORT, "Nothing Added Coffee", 0.50, null);
        Assertions.assertNotNull(testCoffee.getIngredientList());
        Assertions.assertNull(testCoffeeNoIngredients.getIngredientList());

        String readOut = testCoffee.readOutIngredients();
        String nothingThereReadOut = testCoffeeNoIngredients.readOutIngredients();

        Assertions.assertNotNull(testCoffee.readOutIngredients());
        Assertions.assertEquals(readOut, testCoffee.readOutIngredients());
        Assertions.assertNotNull(testCoffeeNoIngredients.readOutIngredients());
        Assertions.assertEquals(nothingThereReadOut, testCoffeeNoIngredients.readOutIngredients());
        Assertions.assertEquals(nothingThereReadOut, "No ingredients listed.");
    }
}
