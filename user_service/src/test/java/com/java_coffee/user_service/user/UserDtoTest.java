package com.java_coffee.user_service.user;

import java.time.LocalDateTime;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.java_coffee.user_service.user.constants.UserType;

public class UserDtoTest {
    UserDto testUser;

    @BeforeEach
    void setUp() {
        testUser = new UserDto("Billy_Bob", "B-B@gmail.com", "ieaugkfs236432");
    }

    @AfterEach
    void tearDown() {
        testUser = null;
    }

    @Test
    void testGetUserId() {
        // user id should be set by the database, not the object
        Assertions.assertEquals(0,testUser.getUserId());
    }

    @Test
    void testGetUserName() {
        String userName = testUser.getUserName();

        Assertions.assertNotNull(userName);
        Assertions.assertEquals("Billy_Bob", userName);
    }

    @Test
    void testSetUserName() {
        String oldName = "Billy_Bob";
        String newName = "Jim_Bob";
        testUser.setUserName(newName);

        Assertions.assertNotEquals(oldName, newName);
        Assertions.assertNotEquals(oldName, testUser.getUserName());
        Assertions.assertEquals(newName, testUser.getUserName());
    }

    @Test
    void testGetUserType() {
        Assertions.assertNotNull(testUser.getUserType());
        Assertions.assertEquals(UserType.USER, testUser.getUserType());
    }

    @Test
    void testSetUserType() {
        testUser.setUserType(UserType.ADMIN);

        Assertions.assertNotNull(testUser.getUserType());
        Assertions.assertNotEquals(UserType.USER, testUser.getUserType());
        Assertions.assertEquals(UserType.ADMIN, testUser.getUserType());
    }

    @Test
    void testGetEmailAddress() {
        String currentEmail = testUser.getEmailAddress();

        Assertions.assertNotNull(testUser.getEmailAddress());
        Assertions.assertEquals(currentEmail, testUser.getEmailAddress());
    }

    @Test
    void testSetEmailAddress() {
        String oldEmail = testUser.getEmailAddress();
        String newEmail = "new_email_Adresss@gmail.com";

        testUser.setEmailAddress(newEmail);

        Assertions.assertNotNull(testUser.getEmailAddress());
        Assertions.assertNotEquals(oldEmail, newEmail);
        Assertions.assertNotEquals(oldEmail, testUser.getEmailAddress());
        Assertions.assertEquals(newEmail, testUser.getEmailAddress());
    }

    @Test 
    void testGetFirstName() {
        // user name is not initially set by contructor
        Assertions.assertNull(testUser.getFirstName());
    }

    @Test
    void testSetFirstName() {
        String newName = "Billy";

        testUser.setFirstName(newName);

        Assertions.assertNotNull(testUser.getFirstName());
        Assertions.assertEquals(newName, testUser.getFirstName());
    }

    @Test
    void testGetLastName() {
        // last name should initially not be set
        Assertions.assertNull(testUser.getLastName());
    }

    @Test
    void testSetLastName() {
        String newName = "Bob";

        testUser.setLastName(newName);

        Assertions.assertNotNull(testUser.getLastName());
        Assertions.assertEquals(newName, testUser.getLastName());
    }

    @Test
    void testGetPassword() {
        String currentPw = testUser.getPassword();
        
        Assertions.assertNotNull(testUser.getPassword());
        Assertions.assertEquals(currentPw.length(), testUser.getPassword().length());
        Assertions.assertEquals(currentPw, testUser.getPassword());
    }

    @Test
    void testSetPassword() {
        String oldPW = testUser.getPassword();
        String newPW = "alfkjh32865212jsjxza";

        testUser.setPassword(newPW);

        Assertions.assertNotNull(testUser.getPassword());
        Assertions.assertNotEquals(oldPW, newPW);
        Assertions.assertNotEquals(oldPW, testUser.getPassword());
        Assertions.assertEquals(newPW, testUser.getPassword());
    }

    @Test
    void testGetJoinDate() {
        // joinDate should be null for freshly created DTOs
        Assertions.assertNull(testUser.getJoinDate());
    }

    @Test
    void testSetJoinDate() {
        LocalDateTime testJoinDate = LocalDateTime.now();
        testUser.setJoinDate(testJoinDate);

        Assertions.assertNotNull(testUser.getJoinDate());
        Assertions.assertEquals(testJoinDate, testUser.getJoinDate());
    }

    @Test
    void testGetIsBanned() {
        Assertions.assertNotNull(testUser.getIsBanned());
        Assertions.assertFalse(testUser.getIsBanned());
    }

    @Test
    void testSetIsBanned() {
        testUser.setIsBanned(true);

        Assertions.assertNotNull(testUser.getIsBanned());
        Assertions.assertTrue(testUser.getIsBanned());

        testUser.setIsBanned(false);

        Assertions.assertNotNull(testUser.getIsBanned());
        Assertions.assertFalse(testUser.getIsBanned());
    }

    @Test
    void testBanHammer() {
        testUser.banHammer();

        Assertions.assertNotNull(testUser.getIsBanned());
        Assertions.assertTrue(testUser.getIsBanned());
    }

    @Test
    void testGetIsSuspended() {
        Assertions.assertNotNull(testUser.getIsSuspended());
        Assertions.assertFalse(testUser.getIsSuspended());
    }

    @Test
    void testSetIsSuspended() {
        testUser.setIsSuspended(true);

        Assertions.assertNotNull(testUser.getIsSuspended());
        Assertions.assertTrue(testUser.getIsSuspended());

        testUser.setIsSuspended(false);
        
        Assertions.assertNotNull(testUser.getIsSuspended());
        Assertions.assertFalse(testUser.getIsSuspended());
    }

    @Test
    void testGetSuspensionDate() {
        // should be null for new user DTOs
        Assertions.assertNull(testUser.getSuspensionDate());
    }

    @Test
    void testSetSuspensionDate() {
        LocalDateTime testDate = LocalDateTime.now();

        testUser.setSuspensionDate(testDate);

        Assertions.assertNotNull(testUser.getSuspensionDate());
        Assertions.assertEquals(testDate, testUser.getSuspensionDate());
    }

    @Test
    void testGetIsConfirmed() {
        Assertions.assertNotNull(testUser.getIsConfirmed());
        Assertions.assertFalse(testUser.getIsConfirmed());
    }

    @Test
    void testSetIsConfirmed() {
        testUser.setIsConfirmed(true);

        Assertions.assertNotNull(testUser.getIsConfirmed());
        Assertions.assertTrue(testUser.getIsConfirmed());

        testUser.setIsConfirmed(false);
        Assertions.assertNotNull(testUser.getIsConfirmed());
        Assertions.assertFalse(testUser.getIsConfirmed());
    }
}
