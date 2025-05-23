package com.example.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UserTest {

    @Test
    public void testPasswordHashingAndCheck() {
        User user = new User("Test", "test@example.com", "mypassword");

        assertNotEquals("mypassword", user.getPassword());
        assertTrue(user.checkPassword("mypassword"));
        assertFalse(user.checkPassword("wrongpassword"));
    }

    @Test
    public void testUserFields() {
        User user = new User("Alice", "alice@example.com", "password123");

        assertEquals("Alice", user.getName());
        assertEquals("alice@example.com", user.getEmail());
        assertNotNull(user.getId());
    }
}
