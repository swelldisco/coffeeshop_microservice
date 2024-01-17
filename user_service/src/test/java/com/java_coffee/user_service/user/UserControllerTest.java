package com.java_coffee.user_service.user;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

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
import com.java_coffee.user_service.user.constants.UserType;

import jakarta.servlet.ServletContext;

@WebMvcTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
public class UserControllerTest {
    
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private WebApplicationContext context;
    @MockBean
    private UserService testService;
    @Autowired
    private ObjectMapper objectMapper;

    private List<UserDto> testUserList;

    @BeforeEach
    public void mockContext() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context)
            .build();

        UserDto user1 = new UserDto(0, "Billy_Bob", UserType.USER, "B-B@gmail.com", null, null, LocalDateTime.now(), false, false, null, null, true);
        user1.setPassword("ieaugkfs236432");

        UserDto user2 = new UserDto(1, "Jimbo", UserType.USER, "JimmyMcBob@mail.ru", null, null, LocalDateTime.now(), false, false, null, null, true);
        user2.setPassword("uwoiagksdjbx");

        UserDto user3 = new UserDto(2, "Bouregard_Bubba", UserType.USER, "OtherB-B@gmail.com", null, null, LocalDateTime.now(), false, false, null, null, true);
        user3.setPassword("weioufgksdjhx");

        UserDto user4 = new UserDto(3, "Cleeeeeeeeetus", UserType.USER, "DonCLEETS@mail.ru", null, null, LocalDateTime.now(), false, false, null, null, true);
        user4.setPassword("wafegdsjhu");

        UserDto user5 = new UserDto(4, "Junior", UserType.USER, "JethroJr@yahoo.com", null, null, LocalDateTime.now(), false, false, null, null, true);
        user5.setPassword("iweluafkjbeiugd");

        testUserList = Arrays.asList(
            user1, user2, user3, user4, user5
        );
    }

    @AfterEach
    public void tearDown() {
        this.mockMvc = null;
        testUserList = null;
    }

    @Test
	public void contextLoads() {
		assertNotNull(mockMvc);
		ServletContext servletContext = context.getServletContext();

        assertNotNull(servletContext);
        assertTrue(servletContext instanceof MockServletContext);
        assertNotNull(context.getBean("userController"));
	}

    @Test
    public void testCreateUser() throws Exception {
        // given
        LocalDateTime testTime = LocalDateTime.now();
        UserDto testDto = new UserDto(1, "Billy_Bob", UserType.USER, "B-B@gmail.com", null, null, testTime, false, false, null, null, true);
        testDto.setPassword("ieaugkfs236432");

        // when
        // stub the testService
        when(testService.createUser(any(UserDto.class))).thenReturn(testDto);

        RequestBuilder rq = MockMvcRequestBuilders.post("/api_v1/users/create")
            .content(objectMapper.writeValueAsString(testDto))
            .contentType(MediaType.APPLICATION_JSON_VALUE);

        // then
        mockMvc.perform(rq)
            .andExpect(MockMvcResultMatchers.status().isCreated())
            .andExpect(MockMvcResultMatchers.jsonPath("$.userName").value(testDto.getUserName()))
            .andExpect(MockMvcResultMatchers.jsonPath("$.emailAddress").value(testDto.getEmailAddress()))
            .andReturn();
    }

    @Test
    public void testGetUserById() throws Exception {
        // given
        Assertions.assertNotNull(testUserList);
        int testId = 1;
        String testUrl = "/api_v1/users/id?userId=" + testId;
        String testUserName = testUserList.get(testId).getUserName();
        String testEmailAddress = testUserList.get(testId).getEmailAddress();

        // when
        when(testService.getUserById(testId)).thenReturn(testUserList.get(testId));

        // then
        this.mockMvc.perform(MockMvcRequestBuilders.get(testUrl))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.userName").value(testUserName))
            .andExpect(MockMvcResultMatchers.jsonPath("$.emailAddress").value(testEmailAddress))
            .andReturn();
    }

    @Test
    public void testGetUserByUserName() throws Exception {
        // given
        Assertions.assertNotNull(testUserList);
        String testUserName = testUserList.get(0).getUserName();
        String testUrl = "/api_v1/users/userName?name=" + testUserName;
        String testUserEmail = testUserList.get(0).getEmailAddress();

        // when
        when(testService.getUserByName(testUserName)).thenReturn(testUserList.stream()
            .filter(u -> u.getUserName().equalsIgnoreCase(testUserName))
            .findFirst()
            .orElse(null));

        // then
        this.mockMvc.perform(MockMvcRequestBuilders.get(testUrl))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.emailAddress").value(testUserEmail))
            .andReturn();
    }

    @Test
    public void testGetUserByEmailAddress() throws Exception {
        // given
        Assertions.assertNotNull(testUserList);
        String testUserEmail = testUserList.get(0).getEmailAddress();
        String testUrl = "/api_v1/users/email?emailAddress=" + testUserEmail;
        String testUserName = testUserList.get(0).getUserName();

        // when
        when(testService.getUserByEmail(testUserEmail)).thenReturn(testUserList.stream()
            .filter(u -> u.getEmailAddress().equalsIgnoreCase(testUserEmail))
            .findFirst()
            .orElse(null));

        // then
        this.mockMvc.perform(MockMvcRequestBuilders.get(testUrl))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.userName").value(testUserName))
            .andReturn();
    }

    @Test
    public void testGetAllUsers() throws Exception {
        // given
        Assertions.assertNotNull(testUserList);

        // when
        when(testService.getAllUsers()).thenReturn(testUserList);

        // then
        this.mockMvc.perform(MockMvcRequestBuilders.get("/api_v1/users/all"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.size()").value(testUserList.size()))
            .andReturn();
    }

    @Test
    public void testUpdateUser() throws Exception{
        // given
        Assertions.assertNotNull(testUserList);
        int testId = 1;
        String testUrl = "/api_v1/users/" + testId;
        UserDto updatedUser = testUserList.get(testId);
        Assertions.assertEquals(testId, updatedUser.getUserId());
        String newFirstName = "Billy";
        updatedUser.setFirstName(newFirstName);
        String newEmail = "aNewEmailAddress.gmail.com";
        updatedUser.setEmailAddress(newEmail);

        // when
        when(testService.updateUser(testId, updatedUser)).thenReturn(updatedUser);

        RequestBuilder rq = MockMvcRequestBuilders.put(testUrl)
            .content(objectMapper.writeValueAsString(updatedUser))
            .contentType(MediaType.APPLICATION_JSON_VALUE);

        // then
        this.mockMvc.perform(rq)
            .andExpect(MockMvcResultMatchers.status().isAccepted())
            .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value(newFirstName))
            .andExpect(MockMvcResultMatchers.jsonPath("$.emailAddress").value(newEmail))
            .andReturn();
    }

}
