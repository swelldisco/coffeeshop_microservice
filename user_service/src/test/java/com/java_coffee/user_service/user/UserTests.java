package com.java_coffee.user_service.user;

import java.time.LocalDateTime;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.java_coffee.user_service.user.constants.Role;

public class UserTests {
    User testUser;
    @BeforeEach
    void setUp() {
        testUser = new User("Billy_Bob", "B-B@gmail.com", "ieaugkfs236432");
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
        Assertions.assertNotNull(testUser.getRole());
        Assertions.assertEquals(Role.USER, testUser.getRole());
    }

    @Test
    void testSetUserType() {
        testUser.setRole(Role.ADMIN);

        Assertions.assertNotNull(testUser.getRole());
        Assertions.assertNotEquals(Role.USER, testUser.getRole());
        Assertions.assertEquals(Role.ADMIN, testUser.getRole());
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

    // getSalt() doesn't exist and probably shouldn't, so this is just to check that salts are being properly generated and reset
    // @Test
    // void testResetSalt() {
    //     String salt1 = testUser.getSalt();
        
    //     testUser.resetSalt();

    //     Assertions.assertNotEquals(salt1, testUser.getSalt());
    // }

    // @Test
    // void testGetSalt() {
    //     Assertions.assertNotNull(testUser.getSalt());
    //     Assertions.assertEquals(88, testUser.getSalt().length());
    // }

    @Test
    void testGetPasswordHash() {
        Assertions.assertNotNull(testUser.getPasswordHash());
        Assertions.assertEquals(344, testUser.getPasswordHash().length());
    }

    @Test
    void testSetPasswordHash() {
        String oldPW = testUser.getPasswordHash();
        String newPw = "liagjvsz42861721";

        testUser.setPasswordHash(newPw);
        String newHash = testUser.getPasswordHash();

        Assertions.assertNotNull(testUser.getPasswordHash());
        Assertions.assertNotEquals(oldPW, newPw);
        Assertions.assertNotEquals(oldPW, testUser.getPasswordHash());
        Assertions.assertNotEquals(newPw, testUser.getPasswordHash());
        Assertions.assertNotEquals(newPw, newHash);
        Assertions.assertEquals(newHash, testUser.getPasswordHash());
        Assertions.assertEquals(344, testUser.getPasswordHash().length());
    }

    @Test
    void testGetJoinDate() {
        Assertions.assertNotNull(testUser.getJoinDate());
        Assertions.assertInstanceOf(LocalDateTime.class, testUser.getJoinDate());
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
        Assertions.assertNotNull(testUser.getSuspensionDate());
        Assertions.assertInstanceOf(LocalDateTime.class, testUser.getSuspensionDate());

        testUser.setIsSuspended(false);
        
        Assertions.assertNotNull(testUser.getIsSuspended());
        Assertions.assertFalse(testUser.getIsSuspended());
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

    @Test
    void testVerifyPassword() {
        String currentPW = "ieaugkfs236432";
        String wrongPW = "akghke37258e";

        boolean bool1 = testUser.verifyPassword(wrongPW);
        boolean bool2 = testUser.verifyPassword(currentPW);

        Assertions.assertFalse(bool1);
        Assertions.assertTrue(bool2);
    }
 
}
