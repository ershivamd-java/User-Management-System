package com.gajendra.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.gajendra.entity.User;
import com.gajendra.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    void getUserById_ShouldReturnUser() {

        User user = new User();
        user.setId(5L);
        user.setName("Anurag Rajpoot");
        user.setEmail("anurag@test.com");

        when(userRepository.findById(5L))
                .thenReturn(Optional.of(user));

        User result = userService.getUserById(5L);

        assertEquals(5L, result.getId());
        assertEquals("Anurag Rajpoot", result.getName());
        assertEquals("anurag@test.com", result.getEmail());
    }
}