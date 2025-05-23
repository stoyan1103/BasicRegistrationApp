package com.example.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ValidatorTest {

    @Test
    public void testValidEmail() {
        assertDoesNotThrow(() -> Validator.validateEmail("test@example.com"));
    }

    @Test
    public void testInvalidEmail() {
        assertThrows(IllegalArgumentException.class, () -> Validator.validateEmail("invalid-email"));
    }
}
