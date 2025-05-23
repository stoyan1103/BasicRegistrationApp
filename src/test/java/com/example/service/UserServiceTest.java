package com.example.service;

import com.example.model.User;
import com.example.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class UserServiceTest {

    private UserRepository userRepository;
    private UserService userService;

    @BeforeEach
    public void setup() {
        userRepository = mock(UserRepository.class);
        userService = new UserService(userRepository);
    }

    @Test
    public void testRegisterUserSuccess() throws Exception {
        when(userRepository.existsByEmail("john@example.com")).thenReturn(false);
        when(userRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        boolean result = userService.registerUser("John", "john@example.com", "securepass");

        assertTrue(result);
        verify(userRepository).save(any());
    }

    @Test
    public void testRegisterUserDuplicateEmail() throws Exception {
        when(userRepository.existsByEmail("john@example.com")).thenReturn(true);

        assertThrows(IllegalStateException.class, () ->
                userService.registerUser("John", "john@example.com", "securepass"));
    }

    @Test
    public void testLoginSuccess() throws Exception {
        User mockUser = new User("John", "john@example.com", "securepass");

        when(userRepository.findByEmail("john@example.com")).thenReturn(mockUser);

        assertTrue(userService.login("john@example.com", "securepass"));
    }

    @Test
    public void testLoginWrongPassword() throws Exception {
        User mockUser = new User("John", "john@example.com", "securepass");

        when(userRepository.findByEmail("john@example.com")).thenReturn(mockUser);

        assertFalse(userService.login("john@example.com", "wrongpass"));
    }

    @Test
    public void testLoginUserNotFound() throws Exception {
        when(userRepository.findByEmail("notfound@example.com")).thenReturn(null);

        assertFalse(userService.login("notfound@example.com", "any"));
    }
}
