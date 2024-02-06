package com.java_coffee.coffee_service.coffeeOrderTests;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
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

import com.java_coffee.coffee_service.cart.Cart;
import com.java_coffee.coffee_service.coffee.Coffee;
import com.java_coffee.coffee_service.coffee.constants.CoffeeSize;
import com.java_coffee.coffee_service.coffeeOrder.CoffeeOrder;
import com.java_coffee.coffee_service.coffeeOrder.CoffeeOrderDto;
import com.java_coffee.coffee_service.coffeeOrder.CoffeeOrderRepository;
import com.java_coffee.coffee_service.coffeeOrder.CoffeeOrderServiceImpl;
import com.java_coffee.coffee_service.mapper.CoffeeMapper;
import com.java_coffee.coffee_service.userStub.UserStub;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class TestCoffeeOrderServiceImpl {
    
    private CoffeeMapper mapper = new CoffeeMapper();
    
    @Mock(name = "database")
    private CoffeeOrderRepository repo;

    @InjectMocks
    private CoffeeOrderServiceImpl service = new CoffeeOrderServiceImpl(mapper, repo);

    UserStub testUser1;
    UserStub testUser2;
    UserStub testUser3;
    Cart testCart1;
    Cart testCart2;
    Cart testCart3;
    Coffee testCoffee1;
    Coffee testCoffee2;
    Coffee testCoffee3;
    List<CoffeeOrder> testList;

    @BeforeEach
    public void setUp() {
        testUser1 = new UserStub(0L, "Billy_Bob");
        testUser2 = new UserStub(1L, "Jim_Bob");
        testUser3 = new UserStub(2L, "Bubba");
        testCart1 = new Cart(0L, testUser1.getUserId(), new ArrayList<CoffeeOrder>());
        testCart2 = new Cart(1L, testUser2.getUserId(), new ArrayList<CoffeeOrder>());
        testCart3 = new Cart(2L, testUser3.getUserId(), new ArrayList<CoffeeOrder>());
        testCoffee1 = new Coffee(0L, CoffeeSize.SHORT, "Test Latte", 1.50, null);
        testCoffee2 = new Coffee(1L, CoffeeSize.GRANDE, "Test Mocha", 1.50, null);
        testCoffee3 = new Coffee(2L, CoffeeSize.TALL, "Test Macchiato", 1.50, null);

        testList = new ArrayList<CoffeeOrder>();

        testList.add(new CoffeeOrder(0L, testCoffee1, 0L, 0L, 3, LocalDateTime.now()));
        testList.add(new CoffeeOrder(1L, testCoffee2, 0L, 0L, 1, LocalDateTime.now()));
        testList.add(new CoffeeOrder(2L, testCoffee2, 1L, 1L, 1, LocalDateTime.now()));
        testList.add(new CoffeeOrder(3L, testCoffee1, 1L, 1L, 1, LocalDateTime.now()));
        testList.add(new CoffeeOrder(4L, testCoffee3, 1L, 1L, 1, LocalDateTime.now()));
        testList.add(new CoffeeOrder(5L, testCoffee1, 2L, 2L, 1, LocalDateTime.now()));
        
    }

    @AfterEach
    public void tearDown() {
        testUser1 = null;
        testUser2 = null;
        testUser3 = null;
        testCart1 = null;
        testCart2 = null;
        testCart3 = null;
        testCoffee1 = null;
        testCoffee2 = null;
        testCoffee3 = null;
    }

    @Test
    public void testCreateOrder() {
        // given
        Assertions.assertNotNull(testCart1);
        Assertions.assertNotNull(testCoffee1);
        String testCoffeeName = testCoffee1.getDrinkName();
        CoffeeOrder tempOrder1 = new CoffeeOrder(0L, testCoffee1, testCart1.getCartId(), testCart1.getuserId(), 1, LocalDateTime.now());
        CoffeeOrderDto tempDto = mapper.mapToCoffeeOrderDto(tempOrder1);

        // when
        when(repo.save(any(CoffeeOrder.class))).thenReturn(tempOrder1);
        CoffeeOrder tempOrder2 = mapper.mapToCoffeeOrder(service.createOrder(tempDto));

        // then
        Assertions.assertNotNull(tempOrder2);
        Assertions.assertEquals(tempOrder1.getCoffee().getDrinkName(), testCoffeeName);
    }

    @Test
    public void testGetOrderByOrderId() {
        // given
        Assertions.assertNotNull(testList);
        int testId = 1;
        Assertions.assertNotNull(testList.get(testId));
        CoffeeOrder temp = testList.get(testId);
        Assertions.assertNotNull(temp);

        // when
        when(repo.findById((long)testId)).thenReturn(Optional.of(testList.get(testId)));
        CoffeeOrder temp2 = mapper.mapToCoffeeOrder(service.getOrderByOrderId(testId));

        // then
        Assertions.assertNotNull(temp2);
        Assertions.assertEquals(testId, temp2.getOrderId());
        Assertions.assertEquals(temp, temp2);
    }

    @Test
    public void testGetAllOrders() {
        // given
        Assertions.assertNotNull(testList);

        // when
        when(repo.findAll()).thenReturn(testList);
        List<CoffeeOrder> tempList = service.getAllOrders().stream()
            .map(c -> mapper.mapToCoffeeOrder(c))
            .toList();

        // then
        Assertions.assertNotNull(tempList);
        Assertions.assertEquals(testList.size(), tempList.size());
        Assertions.assertEquals(testList.get(1), tempList.get(1));

    }

    @Test
    public void testGetOrdersByCartId() {
        // given
        Assertions.assertNotNull(testList);
        Assertions.assertNotNull(testCart2);
        int testId = (int)testCart2.getCartId();

        // when
        when(repo.findByCartId((long)testId)).thenReturn(testList.stream()
            .filter(o -> o.getCartId()== (long)testId)
            .toList()
        );
        List<CoffeeOrder> tempList = service.getOrderByCartId(testId).stream()
            .map(o -> mapper.mapToCoffeeOrder(o))
            .toList();

        // then
        Assertions.assertNotNull(tempList);
        Assertions.assertEquals(tempList.get(0).getCartId(), testId);
    }

    @Test
    public void testGetOrdersByUserId() {
        // given
        Assertions.assertNotNull(testList);
        Assertions.assertNotNull(testUser1);
        long tempId = testUser1.getUserId();
        Assertions.assertNotNull(tempId);
        
        // when
        when(repo.findByUserId((long)tempId)).thenReturn(testList.stream()
            .filter(o -> o.getUserId() == tempId)
            .toList()
        );
        List<CoffeeOrder> tempList = service.getOrderByUserId((long)tempId).stream().map(o -> mapper.mapToCoffeeOrder(o)).toList();

        // then
        Assertions.assertNotNull(tempList);
        Assertions.assertEquals(tempList.get(0).getUserId(), tempId);

    }

    @Test
    public void testUpdateCoffeeOrder() {
        // given
        Assertions.assertNotNull(testList);
        int testId = 1;
        CoffeeOrder tempOrder1 = new CoffeeOrder(testList.get(testId));
        Assertions.assertNotNull(tempOrder1);
        int oldQty = tempOrder1.getQuantity();
        int newQty = 5;
        Assertions.assertNotEquals(oldQty, newQty);
        tempOrder1.setQuantity(newQty);
        CoffeeOrderDto tempDto = mapper.mapToCoffeeOrderDto(tempOrder1);

        // when
        when(repo.save(any(CoffeeOrder.class))).thenReturn(tempOrder1);
        when(repo.existsByOrderId((long)testId)).thenReturn(testList.stream()
            .anyMatch(o -> o.getOrderId() == testId));
        when(repo.findById((long)testId)).thenReturn(testList.stream()
            .filter(o -> o.getOrderId() == testId)
            .findFirst());
        CoffeeOrder tempOrder2 = mapper.mapToCoffeeOrder(service.updateOrder((long)testId, tempDto));

        // then
        Assertions.assertNotNull(tempOrder2);
        Assertions.assertEquals(tempOrder2.getOrderId(), testId);
        Assertions.assertEquals(tempOrder2.getQuantity(), newQty);
        Assertions.assertNotEquals(oldQty, tempOrder2.getQuantity());

    }

    @Test
    public void testDeleteOrderById() {
        // given
        Assertions.assertNotNull(testList);
        int tempId = 1;
        Assertions.assertNotNull(testList.get(tempId));
        
        // when
        when(repo.existsByOrderId((long)tempId)).thenReturn(testList.stream()
            .anyMatch(o -> o.getOrderId() == tempId));
        doNothing().when(repo).deleteById((long)tempId);
        service.deleteOrderById((long)tempId);

        // then
        verify(repo, times(1)).existsByOrderId((long)tempId);
        verify(repo, times(1)).deleteCoffeeOrderByOrderId((long)tempId);
    }


}
