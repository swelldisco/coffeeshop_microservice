package com.java_coffee.user_service.helpers;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.java_coffee.user_service.security.RandomPassword;

public class RandomPasswordTest {
    private RandomPassword rp;

    @BeforeEach
    void setUp() {
        rp = new RandomPassword();
    }

    @AfterEach
    void tearDown() {
        rp = null;
    }

    @Test
    void testGeneratePassword() {
        // given
        int size = 25;

        // when
        String testPw = rp.generatePassword(size);

        // then
        Assertions.assertEquals(size, testPw.length());
        Assertions.assertNotNull(testPw);
    }
}
