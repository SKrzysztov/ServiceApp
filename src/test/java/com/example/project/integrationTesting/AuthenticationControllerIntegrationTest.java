package com.example.project.integrationTesting;

import com.example.project.user.api.RegisterRequest;
import com.example.project.user.domain.User;
import com.example.project.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@AutoConfigureMockMvc
public class AuthenticationControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void whenRegisterNewUser_thenStatus200() throws Exception {
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUsername("newUser");
        registerRequest.setPassword("newPassword");

        ResultActions result = mockMvc.perform(post("/register")
                .content(asJsonString(registerRequest))
                .contentType(MediaType.APPLICATION_JSON));

        // Sprawdź, czy status odpowiedzi to 200 OK
        result.andExpect(status().isOk())
                .andExpect(content().string(containsString("Registration successful")));

        // Dodatkowe sprawdzenie można dodać, aby zweryfikować zapis w bazie danych
        User newUser = userRepository.findByUsername("newUser").orElse(null);
        assertThat(newUser).isNotNull();
    }

    @Test
    public void whenRegisterExistingUser_thenStatus400() throws Exception {
        // Dodaj użytkownika do bazy danych
        User existingUser = new User();
        existingUser.setUsername("existingUser");
        existingUser.setPassword("existingPassword");
        userRepository.save(existingUser);

        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUsername("existingUser");
        registerRequest.setPassword("newPassword");

        ResultActions result = mockMvc.perform(post("/register")
                .content(asJsonString(registerRequest))
                .contentType(MediaType.APPLICATION_JSON));

        // Sprawdź, czy status odpowiedzi to 400 Bad Request
        result.andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("User with this username already exists")));
    }

    private static String asJsonString(final Object obj) {
        try {
            return new com.fasterxml.jackson.databind.ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
