package com.java_coffee.coffee_service.controllerTests;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.java_coffee.coffee_service.cart.CartDto;
import com.java_coffee.coffee_service.cart.CartService;
import com.java_coffee.coffee_service.coffee.Coffee;
import com.java_coffee.coffee_service.coffee.CoffeeDto;
import com.java_coffee.coffee_service.coffee.CoffeeService;
import com.java_coffee.coffee_service.coffee.MenuItemDto;
import com.java_coffee.coffee_service.coffee.constants.CoffeeSize;
import com.java_coffee.coffee_service.coffeeOrder.CoffeeOrderDto;
import com.java_coffee.coffee_service.coffeeOrder.CoffeeOrderService;
import com.java_coffee.coffee_service.mapper.CoffeeMapper;
import com.java_coffee.coffee_service.pojo.OrderReceipt;
import com.java_coffee.coffee_service.pojo.UserStub;

import jakarta.servlet.ServletContext;

// make sure anything in the CoffeeServiceApplication used for loading test data is commented out or the context will not load and the tests will fail and spew gigantic error messages at you about unsatisfied dependencies
@WebMvcTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
public class CoffeeControllerTest {
    private TestMenuItems testMenuItems = new TestMenuItems();

    private CoffeeMapper mapper = new CoffeeMapper();
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private WebApplicationContext context;
    @MockBean
    private CoffeeService coffeeService;
    @MockBean
    private CartService cartService;
    @MockBean
    private CoffeeOrderService orderService;
    @Autowired
    private ObjectMapper objectMapper;

    private String apiUrl = "/api_v1/coffees";
    private List<CoffeeDto> testCoffeeList;
    private List<MenuItemDto> testMenuList;
    private UserStub testUser1;
    private UserStub testUser2;
    private CartDto testCart1;
    private List<CartDto> testCartList;
    private CoffeeOrderDto testOrder1;
    private CoffeeOrderDto testOrder2;
    private CoffeeOrderDto testOrder3;
    private CoffeeOrderDto testOrder4;
    private List<CoffeeOrderDto> testOrderList;

    @BeforeEach
    public void mockContext() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context)
            .build();

        testCoffeeList = testMenuItems.createLists().stream()
            .map(c -> mapper.mapToCoffeeDto(c))
            .toList();

        Set<String> tempSet = new HashSet<>();
        testMenuList = new ArrayList<>();
        for (Coffee coffee : testMenuItems.createLists()) {
            if (tempSet.add(coffee.getDrinkName())) {
                testMenuList.add(new MenuItemDto(
                    coffee.getCoffeeId(),
                    coffee.getDrinkName(), 
                    String.format("%.2f", coffee.getBasePrice()), 
                    String.format("%.2f", coffee.calculateUpcharge(coffee.getBasePrice(), 
                    CoffeeSize.TALL)), 
                    String.format("%.2f", coffee.calculateUpcharge(coffee.getBasePrice(), 
                    CoffeeSize.GRANDE)), 
                    String.format("%.2f", coffee.calculateUpcharge(coffee.getBasePrice(), CoffeeSize.VENTI))));
            }
        }

        testUser1 = new UserStub(0L, "Billy-Bob", "B-B@gmail.com");
        testUser2 = new UserStub(1L, "Jimbo", "Jimbo2984@gmail.com");
        testCart1 = new CartDto(0L, 0L, new ArrayList<>());

        testCartList = new ArrayList<>();
        testCartList.add(testCart1);
        testCartList.add(new CartDto(1L, 4L, new ArrayList<>()));
        testCartList.add(new CartDto(2L, 5L, new ArrayList<>()));
        testCartList.add(new CartDto(3L, 6L, new ArrayList<>()));
        testCartList.add(new CartDto(4L, 7L, new ArrayList<>()));
        testCartList.add(new CartDto(5L, 8L, new ArrayList<>()));

        testOrder1 = new CoffeeOrderDto(0L, testCoffeeList.get(3), 0L, 0L, 1, testCoffeeList.get(3).price(), LocalDateTime.now(), false);
        testOrder2 = new CoffeeOrderDto(1L, testCoffeeList.get(5), 0L, 0L, 1, testCoffeeList.get(5).price(), LocalDateTime.now(), false);
        testOrder3 = new CoffeeOrderDto(2L, testCoffeeList.get(9), 0L, 0L, 1, testCoffeeList.get(9).price(), LocalDateTime.now(), false);
        testOrder4 = new CoffeeOrderDto(3L, testCoffeeList.get(10), 1L, 1L, 1, testCoffeeList.get(10).price(), LocalDateTime.now(), false);
        
        testOrderList = new ArrayList<>();
        testOrderList.add(testOrder1);
        testOrderList.add(testOrder2);
        testOrderList.add(testOrder3);
        testOrderList.add(testOrder4);
        
    }

    @AfterEach
    public void tearDown() {
        this.mockMvc = null;
        testCoffeeList = null;
        testMenuList = null;
        testUser1 = null;
        testUser2 = null;
        testCart1 = null;
        testCartList = null;
        testOrder1 = null;
        testOrder2 = null;
        testOrder3 = null;
        testOrder4 = null;
        testOrderList = null;
    }

    @Test
	public void contextLoads() {
		assertNotNull(mockMvc);
		ServletContext servletContext = context.getServletContext();

        assertNotNull(servletContext);
        assertTrue(servletContext instanceof MockServletContext);
        assertNotNull(context.getBean("coffeeController"));
	}

    // Coffee tests
    @Test
    public void testCreateCoffee() throws Exception {
        // given
        CoffeeDto testNewCoffee = new CoffeeDto(0L, CoffeeSize.SHORT, "Test Latte", 2.50, 2.50, null);
        String testApi = apiUrl + "/createCoffee";

        // when
        when(coffeeService.createCoffee(any(CoffeeDto.class))).thenReturn(testNewCoffee);
        RequestBuilder rq = MockMvcRequestBuilders.post(testApi)
            .content(objectMapper.writeValueAsString(testNewCoffee))
            .contentType(MediaType.APPLICATION_JSON_VALUE);

        //then
        mockMvc.perform(rq)
            .andExpect(MockMvcResultMatchers.status().isCreated())
            .andExpect(MockMvcResultMatchers.jsonPath("$.drinkName").value(testNewCoffee.drinkName()))
            .andExpect(MockMvcResultMatchers.jsonPath("$.basePrice").value(testNewCoffee.basePrice()))
            .andReturn();
    }

    @Test
    public void testInitializeMenu() throws Exception {
        // given
        String testApi = apiUrl + "/initializeMenu";
        Assertions.assertNotNull(testMenuList);

        // when
        doNothing().when(coffeeService).initializeMenu();

        // then
        this.mockMvc.perform(MockMvcRequestBuilders.get(testApi))
            .andExpect(MockMvcResultMatchers.status().isOk());

    }

    // change menuItems.createLists() to public to test this
    @Test
    public void testGetMenu() throws Exception {
        // given
        String testApi = apiUrl + "/getMenu";
        Assertions.assertNotNull(testMenuList);
        int menuSize = testMenuList.size();
        Assertions.assertTrue(menuSize > 0);

        // when
        when(coffeeService.getMenu()).thenReturn(testMenuList);

        // then
        this.mockMvc.perform(MockMvcRequestBuilders.get(testApi))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.size()").value(menuSize))
            .andReturn();
    }

    @Test
    public void testGetCoffeeById() throws Exception {
        // given
        int testId = 2;
        Assertions.assertNotNull(testCoffeeList);
        Assertions.assertNotNull(testCoffeeList.get(2));
        CoffeeDto testDto = testCoffeeList.get(testId);
        Assertions.assertNotNull(testDto);
        String testApi = apiUrl + "/coffeeId?id=" + testId;

        // when
        when(coffeeService.findCoffeeById(anyLong())).thenReturn(testDto);

        // then
        this.mockMvc.perform(MockMvcRequestBuilders.get(testApi))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.coffeeId").value(testCoffeeList.get(testId).coffeeId()))
            .andExpect(MockMvcResultMatchers.jsonPath("$.drinkName").value(testCoffeeList.get(testId).drinkName()))
            .andReturn();
    }

    @Test
    public void testGetCoffeeByNameAndSize() throws Exception {
        // given
        String testName = "mocha";
        String testSizeString = "tall";
        String testApi = apiUrl + "/coffee?name=" + testName + "&size=" + testSizeString;
        Assertions.assertNotNull(testCoffeeList);
        CoffeeDto testDto = testCoffeeList.stream()
            .filter(c -> c.drinkName().equalsIgnoreCase(testName))
            .filter(c -> c.coffeeSize().toString().equalsIgnoreCase(testSizeString))
            .findFirst()
            .orElse(null);
        Assertions.assertNotNull(testDto);

        // when
        when(coffeeService.findCoffeeByNameAndSize(testName, testSizeString)).thenReturn(testDto);

        // then
        this.mockMvc.perform(MockMvcRequestBuilders.get(testApi))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.coffeeId").value(testDto.coffeeId()))
            .andExpect(MockMvcResultMatchers.jsonPath("$.drinkName").value(testDto.drinkName()))
            .andExpect(MockMvcResultMatchers.jsonPath("$.coffeeSize").value(testDto.coffeeSize().toString().toUpperCase()))
            .andReturn();
    }

    @Test
    public void testGetAllCoffees() throws Exception {
        // given
        String testApi = apiUrl + "/allCoffees";
        Assertions.assertNotNull(testCoffeeList);
        int allTheCoffees = testCoffeeList.size();
        Assertions.assertTrue(allTheCoffees > 0);

        // when
        when(coffeeService.findAllCoffees()).thenReturn(testCoffeeList);

        // then
        this.mockMvc.perform(MockMvcRequestBuilders.get(testApi))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.size()").value(allTheCoffees))
            .andReturn();
    }

    @Test
    public void testGetCoffeesByName() throws Exception {
        // given
        Assertions.assertNotNull(testCoffeeList);
        String testDrinkName = "Macchiato";
        String testApi = apiUrl + "/drinkName?name=" + testDrinkName;
        boolean drinkExists = testCoffeeList.stream()
            .anyMatch(c -> c.drinkName().equalsIgnoreCase(testDrinkName));
        Assertions.assertTrue(drinkExists);
        int drinks = (int)testCoffeeList.stream()
            .filter(c -> c.drinkName().equalsIgnoreCase(testDrinkName))
            .count();
        Assertions.assertTrue(drinks > 0);

        // when
        when(coffeeService.findAllByName(testDrinkName)).thenReturn(testCoffeeList.stream()
            .filter(c -> c.drinkName().equalsIgnoreCase(testDrinkName))
            .toList());

        // then
        this.mockMvc.perform(MockMvcRequestBuilders.get(testApi))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.size()").value(drinks))
            .andReturn();
    }

    @Test
    public void testUpdateCoffee() throws Exception {
        // given
        int testId = 1;
        Assertions.assertNotNull(testCoffeeList.get(testId));
        String testApi = apiUrl + "/updateCoffee/" + testId;
        Coffee testCoffee = mapper.mapToCoffee(testCoffeeList.get(testId));
        Assertions.assertNotNull(testCoffee);
        double oldBasePrice = testCoffee.getBasePrice();
        double oldPrice = testCoffee.getPrice();
        double newBasePrice = 3.50;
        testCoffee.setBasePrice(newBasePrice);
        testCoffee.setPrice();
        double newPrice = testCoffee.getPrice();
        Assertions.assertNotEquals(oldBasePrice, newBasePrice);
        Assertions.assertNotEquals(oldPrice, newPrice);
        CoffeeDto testDto = mapper.mapToCoffeeDto(testCoffee);

        // when
        when(coffeeService.updateCoffee(anyLong(), any(CoffeeDto.class))).thenReturn(testDto);
        RequestBuilder rq = MockMvcRequestBuilders.put(testApi)
            .content(objectMapper.writeValueAsString(testDto))
            .contentType(MediaType.APPLICATION_JSON_VALUE);

        // then
        mockMvc.perform(rq)
            .andExpect(MockMvcResultMatchers.status().isAccepted())
            .andExpect(MockMvcResultMatchers.jsonPath("$.price").value(newPrice))
            .andExpect(MockMvcResultMatchers.jsonPath("$.basePrice").value(newBasePrice))
            .andReturn();
    }

    @Test
    public void testDeleteCoffee() throws Exception {
        //given
        int testId = 1;
        String testApi = apiUrl + "/deleteCoffee/" + testId;

        // when
        doNothing().when(coffeeService).deleteCoffeeById(testId);

        // then
        this.mockMvc.perform(MockMvcRequestBuilders.delete(testApi))
            .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    // Cart tests
    @Test
    public void testCreateCart() throws Exception {
        // given
        String testApi = apiUrl + "/createCart";
        Assertions.assertNotNull(testUser2);
        CartDto testCartDto = new CartDto(1L, testUser2.getUserId(), null);

        // when
        when(cartService.createCart(testUser2)).thenReturn(testCartDto);
        RequestBuilder rq = MockMvcRequestBuilders.post(testApi)
            .content(objectMapper.writeValueAsString(testUser2))
            .contentType(MediaType.APPLICATION_JSON_VALUE);

        // then
        mockMvc.perform(rq)
            .andExpect(MockMvcResultMatchers.status().isCreated())
            .andExpect(MockMvcResultMatchers.jsonPath("$.userId").value(testUser2.getUserId()))
            .andReturn();
    }

    @Test
    public void testGetCartById() throws Exception {
        // given
        int testCartId = 1;
        Assertions.assertNotNull(testCartList.get(testCartId));
        long testUserId = testCartList.get(testCartId).userId();
        Assertions.assertNotNull(testUserId);
        Assertions.assertTrue(testUserId > -1);
        String testApi = apiUrl + "/cartById?id=" + testCartId;

        // when
        when(cartService.getCartById(testCartId)).thenReturn(testCartList.get(testCartId));

        // then
        this.mockMvc.perform(MockMvcRequestBuilders.get(testApi))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.userId").value(testUserId))
            .andReturn();
            
    }

    @Test
    public void testGetCartByUserId() throws Exception {
        // given
        int testUserId = (int)testUser1.getUserId();
        Assertions.assertTrue(testCartList.stream()
            .anyMatch(c -> c.userId() == testUserId));
        int testCartId = (int)testCartList.stream()
            .filter(c -> c.userId() == testUserId)
            .mapToInt(c -> (int)c.cartId())
            .findAny()
            .orElse(-1);
        Assertions.assertTrue(testCartId >= 0);
        String testApi = apiUrl + "/cartByUserId?id=" + testUserId;

        // when
        when(cartService.getCartByUserId(testUserId)).thenReturn(testCartList.stream()
            .filter(c -> c.userId() == testUserId)
            .findAny()
            .orElse(null));

        // then
        this.mockMvc.perform(MockMvcRequestBuilders.get(testApi))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.userId").value(testUserId))
            .andReturn();
    }

    @Test
    public void testGetAllCarts() throws Exception {
        // given
        Assertions.assertNotNull(testCartList);
        int testListSize = testCartList.size();
        Assertions.assertTrue(testListSize > 0);
        String testApi = apiUrl + "/allCarts";

        // when
        when(cartService.getAllCarts()).thenReturn(testCartList);

        // then
        this.mockMvc.perform(MockMvcRequestBuilders.get(testApi))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.size()").value(testListSize))
            .andReturn();
    }

    @Test
    public void testClearCart() throws Exception {
        // given
        int testCartId = 1;
        Assertions.assertNotNull(testCartList.get(testCartId));
        CartDto testDto = testCartList.get(testCartId);
        Assertions.assertNotNull(testDto);
        int testUserId = (int)testCartList.get(testCartId).userId();
        String testApi = apiUrl + "/clearCart?cartId=" + testCartId;

        // when
        when(cartService.clearCart(testCartId)).thenReturn(testDto);

        // then
        this.mockMvc.perform(MockMvcRequestBuilders.get(testApi))
            .andExpect(MockMvcResultMatchers.status().isAccepted())
            .andExpect(MockMvcResultMatchers.jsonPath("$.userId").value(testUserId))
            .andExpect(MockMvcResultMatchers.jsonPath("$.orders.size()").value(0))
            .andReturn();
    }

    @Test
    public void testDeleteCart() throws Exception {
        // given
        int testCartId = 1;
        Assertions.assertNotNull(testCartList);
        Assertions.assertNotNull(testCartList.get(testCartId));
        String testApi = apiUrl + "/deleteCart/" + testCartId;

        // when
        doNothing().when(cartService).deleteCartById(testCartId);

        // then
        this.mockMvc.perform(MockMvcRequestBuilders.delete(testApi))
            .andExpect(MockMvcResultMatchers.status().isNoContent())
            .andReturn();
    }

    // CoffeeOrder tests
    @Test
    public void testCreateOrder() throws Exception {
        // given
        String testApi = apiUrl + "/createOrder";
        Assertions.assertNotNull(testCoffeeList);
        CoffeeOrderDto testOrderDto = new CoffeeOrderDto(6L, testCoffeeList.get(6), 2L, 5L, 3, testCoffeeList.get(6).price() * 3, LocalDateTime.now(), false);
        Assertions.assertNotNull(testOrderDto);

        // when
        when(orderService.createOrder(any(CoffeeOrderDto.class))).thenReturn(testOrderDto);
        RequestBuilder rq = MockMvcRequestBuilders.post(testApi)
            .content(objectMapper.writeValueAsString(testOrderDto))
            .contentType(MediaType.APPLICATION_JSON_VALUE);

        // then
        mockMvc.perform(rq)
            .andExpect(MockMvcResultMatchers.status().isCreated())
            .andExpect(MockMvcResultMatchers.jsonPath("$.orderId").value(testOrderDto.orderId()))
            .andExpect(MockMvcResultMatchers.jsonPath("$.coffeeDto.price").value(testCoffeeList.get(6).price()))
            .andExpect(MockMvcResultMatchers.jsonPath("$.lineItemTotal").value(testOrderDto.lineItemTotal()))
            .andReturn();
    }

    @Test
    public void testGetAllCoffeeOrders() throws Exception {
        // given
        String testApi = apiUrl + "/allOrders";
        Assertions.assertNotNull(testOrderList);
        int orderListSize = testOrderList.size();
        Assertions.assertTrue(orderListSize > 0);

        // when
        when(orderService.getAllOrders()).thenReturn(testOrderList);

        // then
        this.mockMvc.perform(MockMvcRequestBuilders.get(testApi))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.size()").value(orderListSize))
            .andReturn();
    }

    @Test
    public void testGetOrderById() throws Exception {
        // given
        int testOrderId = 1;
        String testApi = apiUrl + "/orderById?orderId=" + testOrderId;
        Assertions.assertNotNull(testOrderList.get(testOrderId));
        int testCartId = (int)testOrderList.get(testOrderId).cartId();
        Assertions.assertNotNull(testCartId);
        int testUserId = (int)testOrderList.get(testOrderId).userId();
        Assertions.assertNotNull(testUserId);

        // when
        when(orderService.getOrderByOrderId(testOrderId)).thenReturn(testOrderList.get(testOrderId));

        // then
        this.mockMvc.perform(MockMvcRequestBuilders.get(testApi))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.cartId").value(testCartId))
            .andExpect(MockMvcResultMatchers.jsonPath("$.userId").value(testUserId))
            .andReturn();
    }

    @Test
    public void testGetOrderByCartId() throws Exception {
        // given
        int testCartId = 0;
        String testApi = apiUrl + "/orderByCartId?id=" + testCartId;
        Assertions.assertNotNull(testCartList.get(testCartId));
        Assertions.assertTrue(testOrderList.stream()
            .anyMatch(o -> o.cartId() == testCartId));
        int tempOrderListSize = (int)testOrderList.stream()
            .filter(o -> o.cartId() == testCartId)
            .count();
        Assertions.assertTrue(tempOrderListSize > 0);
        
        // when
        when(orderService.getOrderByCartId(testCartId)).thenReturn(testOrderList.stream()
            .filter(o -> o.cartId() == testCartId)
            .toList());

        // then
        this.mockMvc.perform(MockMvcRequestBuilders.get(testApi))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.size()").value(tempOrderListSize))
            .andReturn();
        
    }

    @Test
    public void testGetOrdersByUserId() throws Exception {
        // given
        int testUserId = 0;
        String testApi = apiUrl + "/orderByUserId?id=" + testUserId;
        Assertions.assertTrue(testOrderList.stream()
            .anyMatch(o -> o.userId() == testUserId));
        int tempListSize = (int)testOrderList.stream()
            .filter(o -> o.userId() == testUserId)
            .count();
        Assertions.assertTrue(tempListSize > 0);

        // when
        when(orderService.getOrderByUserId(testUserId)).thenReturn(testOrderList.stream()
            .filter(o -> o.userId() ==  testUserId)
            .toList());

        // then
        this.mockMvc.perform(MockMvcRequestBuilders.get(testApi))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.size()").value(tempListSize))
            .andReturn();
    }
    
    @Test
    public void testUpdateOrder() throws Exception {
        // given
        int testOrderId = 1;
        String testApi = apiUrl + "/updateOrder/" + testOrderId;
        Assertions.assertNotNull(testOrderList.get(testOrderId));
        CoffeeOrderDto oldDto = testOrderList.get(testOrderId);
        Assertions.assertNotNull(oldDto);
        CoffeeOrderDto updatedDto = new CoffeeOrderDto(oldDto.orderId(), oldDto.coffeeDto(), oldDto.cartId(), oldDto.userId(), 3, oldDto.coffeeDto().price() * 3, LocalDateTime.now(), false);
        Assertions.assertNotNull(updatedDto);
        Assertions.assertNotEquals(updatedDto.lineItemTotal(), oldDto.lineItemTotal());
        Assertions.assertNotEquals(updatedDto.quantity(), oldDto.quantity());

        // when
        when(orderService.updateOrder(testOrderId, updatedDto)).thenReturn(updatedDto);
        RequestBuilder rq = MockMvcRequestBuilders.put(testApi)
            .content(objectMapper.writeValueAsString(updatedDto))
            .contentType(MediaType.APPLICATION_JSON_VALUE);

        // then
        mockMvc.perform(rq)
            .andExpect(MockMvcResultMatchers.status().isAccepted())
            .andExpect(MockMvcResultMatchers.jsonPath("$.orderId").value(updatedDto.orderId()))
            .andExpect(MockMvcResultMatchers.jsonPath("$.quantity").value(updatedDto.quantity()))
            .andExpect(MockMvcResultMatchers.jsonPath("$.lineItemTotal").value(updatedDto.lineItemTotal()))
            .andReturn();

    }

    @Test
    public void testDeleteOrder() throws Exception {
        // given
        int testOrderId = 1;
        String testApi = apiUrl + "/deleteOrder/" + testOrderId;

        // when
        doNothing().when(orderService).deleteOrderById(testOrderId);

        // then
        this.mockMvc.perform(MockMvcRequestBuilders.delete(testApi))
            .andExpect(MockMvcResultMatchers.status().isNoContent())
            .andReturn();
    }

    @Test
    // this more or less appears to mean I need to return either a pojo or a map<string,double> to create a json response that can be tested with MockMvcResultMatchers
    public void testGetOrdertotal() throws Exception {
        // given
        int testCartId = 0;
        String testApi = apiUrl + "/orderTotal?id=" + testCartId;
        Assertions.assertTrue(testOrderList.stream()
            .anyMatch(o -> o.cartId() == testCartId));
        double testTotal = testOrderList.stream()
            .filter(o -> o.cartId() == testCartId)
            .filter(o -> o.isPaid() == false)
            .mapToDouble(o -> o.lineItemTotal())
            .sum();

        // when
        when(orderService.orderTotal(testCartId)).thenReturn(testOrderList.stream()
            .filter(o -> o.cartId() == testCartId)
            .filter(o -> o.isPaid() == false)
            .mapToDouble(o -> o.lineItemTotal())
            .sum());

        // then
        this.mockMvc.perform(MockMvcRequestBuilders.get(testApi))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.total").value(testTotal))
            .andReturn();
    }

    @Test
    public void testGenerateReceipt() throws Exception {
        // given
        int testCartId = 0;
        String testApi = apiUrl + "/receipt?cartId=" + testCartId;
        Assertions.assertTrue(testOrderList.stream()
            .anyMatch(o -> o.cartId() == testCartId && o.userId() == testUser1.getUserId()));
        List<CoffeeOrderDto> tempList = testOrderList.stream()
            .filter(o -> o.cartId() == testCartId)
            .filter(o -> o.userId() == testUser1.getUserId())
            .filter(o -> o.isPaid() == false)
            .toList();
        Assertions.assertNotNull(tempList);
        Assertions.assertTrue(tempList.size() > 0);
        double tempTotal = tempList.stream()
            .mapToDouble(o -> o.lineItemTotal())
            .sum();
        Assertions.assertNotNull(tempTotal);
        Assertions.assertTrue(tempTotal > 0);
        OrderReceipt testReceipt = new OrderReceipt(testUser1, tempList, tempTotal);

        // when
        when(orderService.orderTotal(testCartId)).thenReturn(tempTotal);
        when(orderService.generateReceipt(testUser1, testCartId)).thenReturn(testReceipt);
        RequestBuilder rq = MockMvcRequestBuilders.get(testApi)
            .content(objectMapper.writeValueAsString(testUser1))
            .contentType(MediaType.APPLICATION_JSON_VALUE);

        // then
        this.mockMvc.perform(rq)
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.userName").value(testUser1.getUserName()))
            .andExpect(MockMvcResultMatchers.jsonPath("$.order.size()").value(tempList.size()))
            .andExpect(MockMvcResultMatchers.jsonPath("$.total").value(tempTotal))
            .andReturn();
    }


}

