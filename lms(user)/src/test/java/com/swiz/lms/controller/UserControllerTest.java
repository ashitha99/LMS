package com.swiz.lms.controller;

import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.swiz.lms.component.JwtTokenProvider;
import com.swiz.lms.dto.UserRegistrationRequest;
import com.swiz.lms.entity.AppUser;
import com.swiz.lms.security.CustomUserDetailsService;
import com.swiz.lms.service.AuthService;
import com.swiz.lms.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private JwtTokenProvider jwtTokenProvider;

    @MockBean
    private CustomUserDetailsService customUserDetailsService;

    @MockBean
    private AuthService authService;

    @Test
    public void testRegisterEndpoint() throws Exception {
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

        mockMvc.perform(post("/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(user)))
                .andDo(print())
                .andExpect(status().isCreated());
                //.andExpect(jsonPath("$.username", is(user.getUsername())));

        verify(userService).registerUser(userRegistrationRequest);
    }

    // Similar tests for login, profile, etc.
}
