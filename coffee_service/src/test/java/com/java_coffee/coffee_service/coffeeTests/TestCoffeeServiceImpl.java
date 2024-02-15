package com.java_coffee.coffee_service.coffeeTests;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import com.java_coffee.coffee_service.coffee.Coffee;
import com.java_coffee.coffee_service.coffee.CoffeeDto;
import com.java_coffee.coffee_service.coffee.CoffeeRepository;
import com.java_coffee.coffee_service.coffee.CoffeeServiceImpl;
import com.java_coffee.coffee_service.coffee.MenuItemDto;
import com.java_coffee.coffee_service.coffee.MenuItems;
import com.java_coffee.coffee_service.coffee.constants.CoffeeSize;
import com.java_coffee.coffee_service.exceptions.CoffeeNotFoundException;
import com.java_coffee.coffee_service.mapper.CoffeeMapper;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class TestCoffeeServiceImpl {
    
    private CoffeeMapper mapper = new CoffeeMapper();
    
    @Mock(name = "database")
    private CoffeeRepository repo;

    private MenuItems menuItems = new MenuItems();

    @InjectMocks
    private CoffeeServiceImpl service = new CoffeeServiceImpl(mapper, repo, menuItems);



    List<Coffee> testCoffeeList;
    CoffeeDto testCoffeeDto;
    Coffee testCoffee;

    @BeforeEach
    public void setUp() {
        List<String> ing1 = Arrays.asList("Espresso", "Skim Milk");
        List<String> ing2 = Arrays.asList("Espresso", "Whole Milk", "Chocolate Syrup");
        List<String> ing3 = Arrays.asList("Espresso", "2% Milk");
        List<String> ing4 = Arrays.asList("Espresso", "Filtered Water");
        List<String> ing5 = Arrays.asList("Espresso");
        testCoffeeList = Arrays.asList(
            new Coffee(0L, CoffeeSize.SHORT,"Mocha", 3.29, ing2),
            new Coffee(1L, CoffeeSize.TALL,"Mocha", 3.29, ing2),
           

            new Coffee(2L, CoffeeSize.SHORT, "Skim Latte", 3.25, ing1),
            new Coffee(3L, CoffeeSize.TALL, "Skim Latte", 3.25, ing1),

            new Coffee(4L, CoffeeSize.SHORT, "Latte", 3.25, ing3),
            new Coffee(5L, CoffeeSize.TALL, "Latte", 3.25, ing3),
            
            new Coffee(6L, CoffeeSize.SHORT, "Macchiato", 3.95, ing4),
            new Coffee(7L, CoffeeSize.TALL, "Macchiato", 3.95, ing4),

            new Coffee(8L, CoffeeSize.SHORT, "Cappuccino", 3.00, ing4),
            new Coffee(9L, CoffeeSize.TALL, "Cappuccino", 3.00, ing4),

            new Coffee(10L, CoffeeSize.SHORT, "Americano", 2.50, ing5),
            new Coffee(11L, CoffeeSize.TALL, "Americano", 2.50, ing5),

            new Coffee(12L, CoffeeSize.SHORT, "Flat White", 2.95, ing3),
            new Coffee(13L, CoffeeSize.TALL, "Flat White", 2.95, ing3),

            new Coffee(14L, CoffeeSize.SHORT, "Red Eye", 4.50, ing5),
            new Coffee(15L, CoffeeSize.TALL, "Red Eye", 4.50, ing5),

            new Coffee(16L, CoffeeSize.SHORT, "Espresso", 2.00, ing5),
            new Coffee(17L, CoffeeSize.TALL, "Espresso", 2.00, ing5)
        );

        testCoffeeDto = new CoffeeDto(0L, CoffeeSize.SHORT, "Test Coffee", 1.50, 0, null);
        testCoffee = new Coffee(0L, CoffeeSize.SHORT, "Test Coffee", 1.50, null);
    }

    @AfterEach
    public void tearDown() {
        testCoffeeList = null;
        testCoffeeDto = null;
        testCoffee = null;
    }

    @Test
    public void testCreateCoffee() {
        // given
        Assertions.assertNotNull(testCoffeeDto);
        List<Coffee> anFreshTestList = new ArrayList<>();
        int testIndex = (int)testCoffee.getCoffeeId();
        anFreshTestList.add(testCoffee);
        Assertions.assertNotNull(anFreshTestList.get(testIndex));

        // when
        when(repo.save(any(Coffee.class))).thenReturn(testCoffee);
        when(repo.findById((long)testIndex)).thenReturn(Optional.of(anFreshTestList.get(testIndex)));
        when(repo.findAll()).thenReturn(anFreshTestList);
        Coffee savedCoffee = mapper.mapToCoffee(service.createCoffee(testCoffeeDto));

        // then
        Assertions.assertNotNull(savedCoffee);
        Assertions.assertEquals(testCoffee, savedCoffee);
        Assertions.assertEquals(savedCoffee.getDrinkName(), testCoffee.getDrinkName());
    }

    @Test
    public void testGetCoffeeById() {
        // given
        Assertions.assertNotNull(testCoffeeList);
        int testIndex = 5;
        Coffee compareCoffee = testCoffeeList.get(testIndex);
        String compareName = compareCoffee.getDrinkName();

        // when
        when(repo.findById((long)testIndex)).thenReturn(Optional.of(testCoffeeList.get(testIndex)));
        Coffee aCoffee = mapper.mapToCoffee(service.findCoffeeById(testIndex));

        // then
        Assertions.assertNotNull(aCoffee);
        Assertions.assertEquals(compareCoffee, aCoffee);
        Assertions.assertEquals(compareName, aCoffee.getDrinkName());
        Assertions.assertThrowsExactly(CoffeeNotFoundException.class, () -> service.findCoffeeById(1278));
    }

    @Test
    public void testGetAllCoffees() {
        // given
        Assertions.assertNotNull(testCoffeeList);
        int repoSize = testCoffeeList.size();

        // when
        when(repo.findAll()).thenReturn(testCoffeeList);
        List<Coffee> checkList = service.findAllCoffees().stream()
            .map(c -> mapper.mapToCoffee(c))
            .toList();

        //then
        Assertions.assertNotNull(checkList);
        Assertions.assertEquals(checkList, testCoffeeList);
        Assertions.assertEquals(checkList.size(), repoSize);
    }

    @Test
    public void testGetAllByCoffeeName() {
        // given
        Assertions.assertNotNull(testCoffeeList);
        String testCoffeeName1 = "Mocha";
        String testCoffeeName2 = "Does Not Exist";

        // when
        when(repo.findAllByDrinkNameIgnoringCase(testCoffeeName1)).thenReturn(testCoffeeList.stream()
            .filter(c -> c.getDrinkName().toLowerCase().contains(testCoffeeName1.toLowerCase()))
            .toList());
        when(repo.findAllByDrinkNameIgnoringCase(testCoffeeName2)).thenReturn(testCoffeeList.stream()
            .filter(c -> c.getDrinkName().toLowerCase().contains(testCoffeeName2.toLowerCase()))
            .toList());
        List<Coffee> mochaList = service.findAllByName(testCoffeeName1).stream()
            .map(c -> mapper.mapToCoffee(c))
            .toList();

        // then
        Assertions.assertThrowsExactly(CoffeeNotFoundException.class, () -> service.findAllByName(testCoffeeName2));
        Assertions.assertNotNull(mochaList);
        Assertions.assertEquals(mochaList.size(), 2);
    }

    @Test
    public void testUpdateCoffee() {
        // given
        Assertions.assertNotNull(testCoffeeList);
        int testId = 3;
        Coffee tempCoffee1 = new Coffee(testCoffeeList.get(testId));
        Assertions.assertNotNull(tempCoffee1);
        Coffee tempCoffee2 = new Coffee(testCoffeeList.get(testId));
        Assertions.assertNotNull(tempCoffee2);
        tempCoffee1.setBasePrice(4.40);
        tempCoffee1.setDrinkName("New Name");
        tempCoffee1.setSize(CoffeeSize.VENTI);
        tempCoffee1.setPrice();
        Assertions.assertNotEquals(tempCoffee1, tempCoffee2);
        CoffeeDto tempDto = mapper.mapToCoffeeDto(tempCoffee1);

        // when
        when(repo.findById((long)testId)).thenReturn(Optional.of(testCoffeeList.get(testId)));
        when(repo.save(any(Coffee.class))).thenReturn(tempCoffee1);
        when(repo.existsByCoffeeId(testId)).thenReturn(testCoffeeList.stream()
            .anyMatch(c -> c.getCoffeeId() == testId));
        Coffee tempCoffee3 = mapper.mapToCoffee(service.updateCoffee((long)testId, tempDto));

        // then
        Assertions.assertNotNull(tempCoffee3);
        Assertions.assertNotEquals(tempCoffee2, tempCoffee3);
        Assertions.assertEquals(tempCoffee1, tempCoffee3);
        Assertions.assertEquals(tempCoffee1.getPrice(), tempCoffee3.getPrice());
    }

    @Test
    public void testDeleteCoffeeById() {
        // given
        Assertions.assertNotNull(testCoffeeList);
        int testIndex = 12;
        
        // when
        when(repo.existsByCoffeeId(testIndex)).thenReturn(testCoffeeList.stream()
            .anyMatch(c -> c.getCoffeeId() == testIndex));
        doNothing().when(repo).deleteByCoffeeId(testIndex);
        service.deleteCoffeeById(testIndex);
        
        // then
        verify(repo, times(1)).existsByCoffeeId(testIndex);
        verify(repo, times(1)).deleteByCoffeeId(testIndex);
    }

    @Test
    public void testInitializeMenu() {
        // given
        Coffee tempCoffee = new Coffee(0L, CoffeeSize.SHORT, "temp coffee", 1.99, null);
        
        // when
        when(repo.save(any(Coffee.class))).thenReturn(tempCoffee);
        service.initializeMenu();

        //
        // MenuItems has 36 coffees in it at the time of this test.  Double check that if this test fails here.
        verify(repo, times(36)).save(any(Coffee.class));
    }

    @Test 
    public void getMenu() {
        // given
        Assertions.assertNotNull(testCoffeeList);
        Assertions.assertEquals(testCoffeeList.stream().filter(c -> c.getDrinkName() == "Latte").count(), 2);
        Assertions.assertEquals(testCoffeeList.stream().filter(c -> c.getDrinkName() == "Red Eye").count(), 2);
        int length = testCoffeeList.size();
        Assertions.assertEquals(length, testCoffeeList.size());
        Assertions.assertTrue(length%2 == 0);

        // when
        when(repo.findAll()).thenReturn(testCoffeeList);
        List<MenuItemDto> tempList = service.getMenu();

        // then
        Assertions.assertNotEquals(length, tempList.size());
        Assertions.assertEquals(length/2, tempList.size());
        Assertions.assertEquals(tempList.stream().filter(c -> c.drinkName() == "Latte").count(), 1);
        Assertions.assertEquals(tempList.stream().filter(c -> c.drinkName() == "Red Eye").count(), 1);
        
    }

}
