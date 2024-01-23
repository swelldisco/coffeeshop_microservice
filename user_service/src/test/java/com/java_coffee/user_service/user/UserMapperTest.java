package com.java_coffee.user_service.user;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import com.java_coffee.user_service.user.constants.Role;

@TestInstance(Lifecycle.PER_CLASS)
public class UserMapperTest {
    private UserMapper mapper;
    
    User testUser;
    UserDto testUserDto;

    @BeforeAll
    void setUp() {
        mapper = new UserMapper();
        testUser = new User("Billy_Bob", "B-B@gmail.com", "ieaugkfs236432");
        testUserDto = new UserDto(0L, "Billy_Bob", Role.USER, "B-B@gmail.com", null, null, "wieufgdjwat23785", null, false, false, null, null, false);
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

        Assertions.assertNotNull(dto.userId());
        Assertions.assertNotNull(dto.userName());
        Assertions.assertEquals(testUser.getUserName(), dto.userName());
        Assertions.assertNotNull(dto.emailAddress());
        Assertions.assertEquals(testUser.getEmailAddress(), dto.emailAddress());
        Assertions.assertEquals(dto.password(), "****");
        Assertions.assertNotEquals(testUser.getPasswordHash(), dto.password());
    }

    @Test
    void testMapToUser() {
        User user = mapper.mapToUser(testUserDto);

        Assertions.assertNotNull(user.getUserId());
        Assertions.assertNotNull(user.getUserName());
        Assertions.assertEquals(testUserDto.userName(), user.getUserName());
        Assertions.assertNotNull(user.getEmailAddress());
        Assertions.assertEquals(testUserDto.emailAddress(), user.getEmailAddress());
        Assertions.assertNull(user.getPasswordHash());
        Assertions.assertNotEquals(testUserDto.password(), user.getPasswordHash());
    }
}
