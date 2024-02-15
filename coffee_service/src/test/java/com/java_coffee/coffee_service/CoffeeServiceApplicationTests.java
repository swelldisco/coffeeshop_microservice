package com.java_coffee.coffee_service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@AutoConfigureMockMvc
@SpringBootTest
class CoffeeServiceApplicationTests {

@Autowired
	private MockMvc mockMvc;
	
	@Test
	void contextLoads() {
		Assertions.assertNotNull(mockMvc);
	}

}
