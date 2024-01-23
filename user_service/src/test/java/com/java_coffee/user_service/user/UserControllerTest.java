package com.java_coffee.user_service.user;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
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
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.java_coffee.user_service.user.constants.Role;
import com.jayway.jsonpath.JsonPath;

import jakarta.servlet.ServletContext;

@WebMvcTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
public class UserControllerTest {
    private UserMapper mapper;
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

        UserDto user1 = new UserDto(1L, "Billy_Bob", Role.USER, "B-B@gmail.com", null, null, "wieufgdjwat23785", null, false, false, null, null, false);

        UserDto user2 = new UserDto(2L ,"Jimbo", Role.USER, "JimmyMcBob@mail.ru", null, null, "uwoiagksdjbx", null, false, false, null, null, false);

        UserDto user3 = new UserDto(3L, "Bouregard_Bubba", Role.USER, "OtherB-B@gmail.com", null, null, "weioufgksdjhx", null, false, false, null, null, false);

        UserDto user4 = new UserDto(4L, "Cleeeeeeeeetus", Role.USER, "DonCLEETS@mail.ru", null, null, "wafegdsjhu", null, false, false, null, null, false);

        UserDto user5 = new UserDto(5L, "Junior", Role.USER, "JethroJr@yahoo.com", null, null, "iweluafkjbeiugd",null, false, false, null, null, false);

        testUserList = Arrays.asList(user1, user2, user3, user4, user5);
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
        UserDto testDto = new UserDto(0L, "Billy_Bob", Role.USER, "B-B@gmail.com", null, null, "wieufgdjwat23785", testTime, false, false, null, null, false);

        // when
        // stub the testService
        when(testService.createUser(any(UserDto.class))).thenReturn(testDto);

        RequestBuilder rq = MockMvcRequestBuilders.post("/api_v1/users/create")
            .content(objectMapper.writeValueAsString(testDto))
            .contentType(MediaType.APPLICATION_JSON_VALUE);

        // then
        mockMvc.perform(rq)
            .andExpect(MockMvcResultMatchers.status().isCreated())
            .andExpect(MockMvcResultMatchers.jsonPath("$.userName").value(testDto.userName()))
            .andExpect(MockMvcResultMatchers.jsonPath("$.emailAddress").value(testDto.emailAddress()))
            .andReturn();
    }

    @Test
    public void testGetUserById() throws Exception {
        // given
        Assertions.assertNotNull(testUserList);
        int testId = 1;
        String testUrl = "/api_v1/users/id?userId=" + testId;
        String testUserName = testUserList.get(testId).userName();
        String testEmailAddress = testUserList.get(testId).emailAddress();

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
        String testUserName = testUserList.get(0).userName();
        String testUrl = "/api_v1/users/userName?name=" + testUserName;
        String testUserEmail = testUserList.get(0).emailAddress();

        // when
        when(testService.getUserByName(testUserName)).thenReturn(testUserList.stream()
            .filter(u -> u.userName().equalsIgnoreCase(testUserName))
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
        String testUserEmail = testUserList.get(0).emailAddress();
        String testUrl = "/api_v1/users/email?emailAddress=" + testUserEmail;
        String testUserName = testUserList.get(0).userName();

        // when
        when(testService.getUserByEmail(testUserEmail)).thenReturn(testUserList.stream()
            .filter(u -> u.emailAddress().equalsIgnoreCase(testUserEmail))
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
        User updatedUser = mapper.mapToUser(new UserDto(2L ,"Jimbo", Role.USER, "JimmyMcBob@mail.ru", null, null, "uwoiagksdjbx", null, false, false, null, null, false));
        String newFirstName = "Jimmy";
        String newEmail = "A_new_Email@mail.ru";
        String oldEmail = updatedUser.getEmailAddress();
        int testId = (int)updatedUser.getUserId();
        String testUrl = "/api_v1/users/" + testId;
        updatedUser.setFirstName(newFirstName);
        updatedUser.setEmailAddress(newEmail);
        UserDto updatedDto = mapper.mapToDto(updatedUser);

        // when
        when(testService.updateUser(anyLong(), any(UserDto.class))).thenReturn(updatedDto);
        RequestBuilder rq = MockMvcRequestBuilders.put(testUrl)
            .content(objectMapper.writeValueAsString(updatedUser))
            .contentType(MediaType.APPLICATION_JSON_VALUE);


        // then
        MvcResult mvcResult = mockMvc.perform(rq)
            .andExpect(MockMvcResultMatchers.status().isAccepted())
            .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value(newFirstName))
            .andExpect(MockMvcResultMatchers.jsonPath("$.emailAddress").value(newEmail))
            .andReturn();

            String response = mvcResult.getResponse().getContentAsString();
            String checkEmail = JsonPath.parse(response).read("$.emailAddress");
            Assertions.assertNotEquals(oldEmail, checkEmail);
            Assertions.assertEquals(newEmail, checkEmail);
    }

    @Test
    public void testDeleteUser() throws Exception {
        // given
        int testId = 1;
        String testUrl = "/api_v1/users/" + testId;

        // when
        doNothing().when(testService).deleteUserById(testId);

        // then
        this.mockMvc.perform(MockMvcRequestBuilders.delete(testUrl))
            .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

}
