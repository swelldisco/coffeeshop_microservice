package com.java_coffee.coffee_service.cartTests;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
import com.java_coffee.coffee_service.cart.CartRepository;
import com.java_coffee.coffee_service.cart.CartServiceImpl;
import com.java_coffee.coffee_service.coffee.Coffee;
import com.java_coffee.coffee_service.coffee.constants.CoffeeSize;
import com.java_coffee.coffee_service.coffeeOrder.CoffeeOrder;
import com.java_coffee.coffee_service.coffeeOrder.CoffeeOrderService;
import com.java_coffee.coffee_service.exceptions.CartNotFoundException;
import com.java_coffee.coffee_service.mapper.CoffeeMapper;
import com.java_coffee.coffee_service.pojo.UserStub;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class TestCartServiceImpl {

    @Mock(name = "coffee_mapper")
    private CoffeeMapper mapper = new CoffeeMapper();
    
    @Mock(name = "order_service")
    private CoffeeOrderService orderService;

    @Mock(name = "database")
    private CartRepository repo;

    @InjectMocks
    private CartServiceImpl service = new CartServiceImpl(mapper, repo, orderService);

    private UserStub testUser1;
    private UserStub testUser2;
    private UserStub testUser3;
    private Cart testCart1;
    private Cart testCart2;
    private Cart testCart3;
    private Coffee testCoffee1;
    private Coffee testCoffee2;
    private Coffee testCoffee3;
    private CoffeeOrder testOrder1;
    private CoffeeOrder testOrder2;
    private CoffeeOrder testOrder3;
    private CoffeeOrder testOrder4;
    private CoffeeOrder testOrder5;
    private CoffeeOrder testOrder6;
    private CoffeeOrder testOrder7;
    private List<Cart> testList;
    private List<CoffeeOrder> testOrderList;

    @BeforeEach
    public void setUp() {
        testUser1 = new UserStub(0L, "Billy_Bob", "b-b@gmail.com");
        testUser2 = new UserStub(1L, "Jim_Bob", "jimbo@mail.ru");
        testUser3 = new UserStub(2L, "Bubba", "bubsy@aol.com");
        testCart1 = new Cart(0L, testUser1.getUserId(), new ArrayList<CoffeeOrder>());
        testCart2 = new Cart(1L, testUser2.getUserId(), new ArrayList<CoffeeOrder>());
        testCart3 = new Cart(2L, testUser3.getUserId(), new ArrayList<CoffeeOrder>());
        testCoffee1 = new Coffee(0L, CoffeeSize.SHORT, "Test Latte", 1.50, null);
        testCoffee2 = new Coffee(1L, CoffeeSize.GRANDE, "Test Mocha", 1.50, null);
        testCoffee3 = new Coffee(2L, CoffeeSize.TALL, "Test Macchiato", 1.50, null);
        testOrder1 = new CoffeeOrder(testCoffee1, testCart1, 3);
        testOrder2 = new CoffeeOrder(testCoffee2, testCart2, 1);
        testOrder3 = new CoffeeOrder(testCoffee3, testCart3, 5);
        testOrder4 = new CoffeeOrder(testCoffee1, testCart2, 1);
        testOrder5 = new CoffeeOrder(testCoffee3, testCart2, 1);
        testOrder6 = new CoffeeOrder(testCoffee2, testCart1, 1);
        testOrder7 = new CoffeeOrder(testCoffee3, testCart1, 1);
        testList = new ArrayList<>();
        testList.add(testCart1);
        testList.add(testCart2);
        testList.add(testCart3);
        
        testCart2.addOrderToCart(testOrder2);

        testOrderList = new ArrayList<>();
        testOrderList.add(testOrder1);
        testOrderList.add(testOrder2);
        testOrderList.add(testOrder3);
        testOrderList.add(testOrder4);
        testOrderList.add(testOrder5);
        testOrderList.add(testOrder6);
        testOrderList.add(testOrder7);
        
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
        testOrder1 = null;
        testOrder2 = null;
        testOrder3 = null;
        testOrder4 = null;
        testOrder5 = null;
        testOrder6 = null;
        testOrder7 = null;
        testList = null;
        testOrderList = null;
    }
    
    @Test
    public void testCreateCart() {
        // given
        Assertions.assertNotNull(testUser1);
        Assertions.assertNotNull(testCart1);
        Assertions.assertEquals(testCart1.getUserId(), testUser1.getUserId());

        // when
        when(repo.save(any(Cart.class))).thenReturn(testCart1);
        Cart tempCart = mapper.mapToCart(service.createCart(testUser1));
        
        // then
        Assertions.assertNotNull(tempCart);
        Assertions.assertEquals(tempCart, testCart1);

    }

    @Test
    public void testGetCardById() {
        // given
        Assertions.assertNotNull(testList);
        int testIndex = 1;
        Cart tempCart = new Cart(testList.get(testIndex));
        Assertions.assertNotNull(tempCart);

        // when
        when(repo.findById((long)testIndex)).thenReturn(testList.stream()
        .filter(c -> c.getCartId() == testIndex)
        .findAny());
        Cart tempCart2 = new Cart(mapper.mapToCart(service.getCartById((long)testIndex)));

        // then
        Assertions.assertNotNull(tempCart2);
        Assertions.assertEquals(tempCart2.getCartId(), testIndex);
        Assertions.assertEquals(tempCart, tempCart2);
        Assertions.assertThrowsExactly(CartNotFoundException.class, () -> service.getCartById(23859L));
    }

    @Test
    public void testGetCartByUserId() {
        // given
        Assertions.assertNotNull(testList);
        Assertions.assertNotNull(testUser1.getUserId());
        int testId = (int)testUser1.getUserId();
        Assertions.assertEquals(testList.get(testId).getUserId(), testUser1.getUserId());

        // when
        when(repo.findCartByUserId(testId)).thenReturn(testList.stream()
            .filter(c -> c.getUserId() == testId)
            .findFirst());
        Cart tempCart = new Cart(mapper.mapToCart(service.getCartByUserId((long)testId)));

        // then
        Assertions.assertNotNull(tempCart);
        Assertions.assertEquals(tempCart.getUserId(), testId);
        Assertions.assertEquals(tempCart.getCartId(), testList.get(testId).getCartId());
        Assertions.assertThrowsExactly(CartNotFoundException.class, () -> service.getCartByUserId(32786L));
    }

    @Test
    public void testGetAllCarts() {
        // given
        Assertions.assertNotNull(testList);
        int listSize = testList.size();

        // when
        when(repo.findAll()).thenReturn(testList);
        List<Cart> tempList = service.getAllCarts().stream()
            .map(c -> mapper.mapToCart(c))
            .toList();

        // then
        Assertions.assertNotNull(tempList);
        Assertions.assertEquals(tempList.size(), listSize);
        Assertions.assertEquals(tempList.get(1), testList.get(1));
    }

    @Test
    public void testClearCart() {
        //given
        Assertions.assertNotNull(testList);
        Assertions.assertNotNull(testCart2.getOrders());
        Assertions.assertEquals(testCart2.getOrders().size(), 1);
        int cartSize = testCart2.getOrders().size();
        int testId = (int)testCart2.getCartId();

        // when
        when(repo.existsByCartId((long)testId)).thenReturn(testList.stream()
            .anyMatch(c -> c.getCartId() == testId));
        when(repo.findById((long)testId)).thenReturn(Optional.of(testCart2));
        when(repo.save(testCart2)).thenReturn(testCart2);
        doNothing().when(orderService).deleteOrderById(anyLong());
        service.clearCart((long)testId);

        // then
        Assertions.assertNotNull(testCart2.getOrders());
        Assertions.assertEquals(testCart2.getOrders().size(), 0);
        verify(orderService, times(cartSize)).deleteOrderById(anyLong());
    }

    @Test
    public void testDeleteCartById() {
        // given
        Assertions.assertNotNull(testList);
        Assertions.assertNotNull(testOrderList);
        int testId = 1;
        Assertions.assertNotNull(testList.get(testId));
        List<CoffeeOrder> tempList = testList.get(testId).getOrders();
        int cartSize = testList.get(testId).getOrders().size();
        Assertions.assertEquals(tempList.size(), cartSize);

        // when
        when(repo.existsByCartId((long)testId)).thenReturn(testList.stream()
            .anyMatch(c -> c.getCartId() == testId));
        when(orderService.getOrderByCartId(testId)).thenReturn(tempList.stream()
            .map(o -> mapper.mapToCoffeeOrderDto(o))
            .toList());
        doNothing().when(orderService).deleteOrderById(anyLong());
        doNothing().when(repo).deleteByCartId((long)testId);
        service.deleteCartById(testId);

        // then
        verify(repo, times(1)).existsByCartId((long)testId);
        verify(repo, times(1)).deleteByCartId((long)testId);
        verify(orderService, times(cartSize)).deleteOrderById(anyLong());
        verify(orderService, times(1)).getOrderByCartId((long)testId);
        Assertions.assertThrowsExactly(CartNotFoundException.class, () -> service.deleteCartById(8523L));
    }


}
