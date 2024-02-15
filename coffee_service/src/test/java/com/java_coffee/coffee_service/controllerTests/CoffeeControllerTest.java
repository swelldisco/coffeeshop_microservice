package com.java_coffee.coffee_service.controllerTests;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.java_coffee.coffee_service.cart.CartService;
import com.java_coffee.coffee_service.coffee.CoffeeService;
import com.java_coffee.coffee_service.coffee.MenuItems;
import com.java_coffee.coffee_service.coffeeOrder.CoffeeOrderService;
import com.java_coffee.coffee_service.mapper.CoffeeMapper;

import jakarta.servlet.ServletContext;

@WebMvcTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
public class CoffeeControllerTest {

    private MenuItems menuItems;
    private CoffeeMapper mapper;
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

    @BeforeEach
    public void mockContext() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context)
            .build();
    }

    @AfterEach
    public void tearDown() {
        this.mockMvc = null;
    }

    @Test
	public void contextLoads() {
		assertNotNull(mockMvc);
		ServletContext servletContext = context.getServletContext();

        assertNotNull(servletContext);
        assertTrue(servletContext instanceof MockServletContext);
        assertNotNull(context.getBean("coffeeController"));
	}
    
}
