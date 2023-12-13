package com.example.project.integrationTesting;

import com.example.project.customservice.domain.CustomService;
import com.example.project.customservice.repository.CustomServiceRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class CustomServiceControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CustomServiceRepository customServiceRepository;

    @Test
    public void whenGetServiceById_thenStatus200() throws Exception {
        CustomService customService = new CustomService();
        customService.setServiceName("Test Service");
        CustomService savedService = customServiceRepository.save(customService);

        ResultActions result = mockMvc.perform(get("http://localhost/api/services/{id}", savedService.getId())
                .contentType(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.serviceName").value("Test Service"));
    }

    @Test
    public void whenGetNonExistingService_thenStatus404() throws Exception {
        // Wykonaj zapytanie GET /service/{id} z id, które nie istnieje
        ResultActions result = mockMvc.perform(get("http://localhost/api/services/{id}", 999)
                .contentType(MediaType.APPLICATION_JSON));

        // Sprawdź, czy status odpowiedzi to 404 Not Found
        result.andExpect(status().isNotFound());
    }
}
