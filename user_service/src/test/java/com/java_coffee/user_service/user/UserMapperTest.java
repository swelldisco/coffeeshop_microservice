package com.java_coffee.user_service.user;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

@TestInstance(Lifecycle.PER_CLASS)
public class UserMapperTest {
    private UserMapper mapper;
    
    User testUser;
    UserDto testUserDto;

    @BeforeAll
    void setUp() {
        mapper = new UserMapper();
        testUser = new User("Billy_Bob", "B-B@gmail.com", "ieaugkfs236432");
        testUserDto = new UserDto("Billy_Bob", "B-B@gmail.com", "ieaugkfs236432");
    }

    @AfterAll
    void tearDown() {
        mapper = null;
        testUser = null;
        testUserDto = null;
    }

    @Test
    void testMapToDto() {
        UserDto dto = mapper.mapToDto(testUser);

        Assertions.assertNotNull(dto.getUserId());
        Assertions.assertNotNull(dto.getUserName());
        Assertions.assertEquals(testUser.getUserName(), dto.getUserName());
        Assertions.assertNotNull(dto.getEmailAddress());
        Assertions.assertEquals(testUser.getEmailAddress(), dto.getEmailAddress());
        Assertions.assertNull(dto.getPassword());
        Assertions.assertNotEquals(testUser.getPasswordHash(), dto.getPassword());
    }

    @Test
    void testMapToUser() {
        User user = mapper.mapToUser(testUserDto);

        Assertions.assertNotNull(user.getUserId());
        Assertions.assertNotNull(user.getUserName());
        Assertions.assertEquals(testUserDto.getUserName(), user.getUserName());
        Assertions.assertNotNull(user.getEmailAddress());
        Assertions.assertEquals(testUserDto.getEmailAddress(), user.getEmailAddress());
        Assertions.assertNull(user.getPasswordHash());
        Assertions.assertNotEquals(testUserDto.getPassword(), user.getPasswordHash());
    }
}
