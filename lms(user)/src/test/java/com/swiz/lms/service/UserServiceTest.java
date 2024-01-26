package com.swiz.lms.service;

import com.swiz.lms.dto.UserRegistrationRequest;
import com.swiz.lms.entity.AppUser;
import com.swiz.lms.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

/*
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }
*/

    @Test
    public void testRegisterUser() {
        // setup
        AppUser user = AppUser.builder()
                .id(1L)
                .username("balaji")
                .email("balaji@hello.com")
                .password("hello_123")
                .build();
        UserRegistrationRequest userRegistrationRequest = UserRegistrationRequest.builder()
                .username("balaji")
                .email("balaji@hello.com")
                .password("hello_123")
                .build();
        when(userRepository.findByEmail(userRegistrationRequest.getEmail())).thenReturn(null);
        when(userRepository.save(any(AppUser.class))).thenReturn(user);
        when(passwordEncoder.encode(userRegistrationRequest.getPassword())).thenReturn("encodedPassword"); // Mock the encoding
        ArgumentCaptor<AppUser> argumentCaptor = ArgumentCaptor.forClass(AppUser.class);


        // execute
        userService.registerUser(userRegistrationRequest);

        // validate
        verify(userRepository).save(argumentCaptor.capture());

        AppUser savedUser = argumentCaptor.getValue();
        // Assert that the savedUser properties match the expected values
        assertEquals("balaji", savedUser.getUsername(), "Username should match");
        assertEquals("balaji@hello.com", savedUser.getEmail(), "Email should match");
        assertEquals("encodedPassword", savedUser.getPassword(), "Password should be encoded");
        // Add assertions to inspect the savedUser object.
    }

    // Similar tests for other methods like finding a user, updating a user, etc.
}
