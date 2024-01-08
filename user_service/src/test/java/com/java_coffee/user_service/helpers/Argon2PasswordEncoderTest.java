package com.java_coffee.user_service.helpers;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class Argon2PasswordEncoderTest {
    private Argon2PasswordEncoder encoder;

    @BeforeEach
    void setUp() {
        encoder = new Argon2PasswordEncoder();
    }

    @AfterEach
    void tearDown() {
        encoder = null;
    }

    @Test
    void testGenerateSalt() {

        // when
        String saltTest = encoder.generateSalt();
        String saltTest2 = encoder.generateSalt();
        // then
        Assertions.assertNotNull(saltTest);
        Assertions.assertEquals(88, saltTest.length());
        Assertions.assertNotEquals(saltTest, saltTest2);
    }

    @Test
    void testHash() {
        // given
        String password = "a@dFsWgDeu$3T7*8T341";
        String salt = encoder.generateSalt();

        // when
        String passwordHash = encoder.hash(password, salt);

        // then
        Assertions.assertNotNull(passwordHash);
        Assertions.assertEquals(344, passwordHash.length());
    }

    @Test
    void testVerify() {
        // given
        String password = "a@dFsWgDeu$3T7*8T341";
        String password2 = "12GaR4sgk#FjwRrGi%;h";
        String salt = encoder.generateSalt();
        String salt2 = encoder.generateSalt();
        String passwordHash = encoder.hash(password, salt);

        // when
        boolean testResult1 = encoder.verify(password2, salt, passwordHash);
        boolean testResult2 = encoder.verify(password, salt2, passwordHash);
        boolean testResult3 = encoder.verify(password2, salt2, passwordHash);
        boolean testResult4 = encoder.verify(password, salt, passwordHash);

        // then
        Assertions.assertNotEquals(salt, salt2);
        Assertions.assertNotNull(salt);
        Assertions.assertFalse(testResult1);
        Assertions.assertFalse(testResult2);
        Assertions.assertFalse(testResult3);
        Assertions.assertTrue(testResult4);
    }
}
