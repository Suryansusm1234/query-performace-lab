package com.surya.dbperformancelab.controller;

import com.surya.dbperformancelab.dto.responseDto.CustomerResponse;
import com.surya.dbperformancelab.exception.ResourceNotFoundException;
import com.surya.dbperformancelab.service.interfaces.CustomerServiceInterface;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Instant;

import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CustomerController.class)
public class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CustomerServiceInterface customerServiceInterface;

    @Test
    void shouldReturnCustomer_WhenIdIsValidAndExists() throws Exception {
        // Given
        Long validId = 123L;
        CustomerResponse expectedResponse = CustomerResponse.builder()
                .id(validId)
                .fullName("John Doe")
                .email("john.doe@example.com")
                .createdAt(Instant.now())
                .build();

        Mockito.when(customerServiceInterface.getCustomerById(eq(validId)))
                .thenReturn(expectedResponse);

        // When & Then
        mockMvc.perform(get("/api/customers/{id}", validId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpectAll(
                        status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        jsonPath("$.id").value(validId),
                        jsonPath("$.fullName").value("John Doe"),
                        jsonPath("$.email").value("john.doe@example.com"),
                        jsonPath("$.createdAt").exists()
                );
    }

    @Test
    void shouldReturn404_WhenCustomerDoesNotExist() throws Exception {
        // Given
        Long nonExistentId = 999L;
        Mockito.when(customerServiceInterface.getCustomerById(eq(nonExistentId)))
                .thenThrow(new ResourceNotFoundException("Customer not found with id: " + nonExistentId));

        // When & Then
        mockMvc.perform(get("/api/customers/{id}", nonExistentId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpectAll(
                        status().isNotFound(),
                        jsonPath("$.status").value(404),
                        jsonPath("$.error").value("Not Found"),
                        jsonPath("$.message").value("Customer not found with id: " + nonExistentId)
                );
    }

    @Test
    void shouldReturn400_WhenIdIsZero() throws Exception {
        // Given
        Long zeroId = 0L;
        Mockito.when(customerServiceInterface.getCustomerById(eq(zeroId)))
                .thenThrow(new IllegalArgumentException("Customer ID must be positive"));

        // When & Then
        mockMvc.perform(get("/api/customers/{id}", zeroId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpectAll(
                        status().isBadRequest(),
                        jsonPath("$.status").value(400),
                        jsonPath("$.error").value("Bad Request"),
                        jsonPath("$.message").value("Customer ID must be positive")
                );
    }

    @Test
    void shouldReturn400_WhenIdIsNegative() throws Exception {
        // Given
        Long negativeId = -123L;
        Mockito.when(customerServiceInterface.getCustomerById(eq(negativeId)))
                .thenThrow(new IllegalArgumentException("Customer ID cannot be negative"));

        // When & Then
        mockMvc.perform(get("/api/customers/{id}", negativeId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpectAll(
                        status().isBadRequest(),
                        jsonPath("$.status").value(400),
                        jsonPath("$.error").value("Bad Request"),
                        jsonPath("$.message").value("Customer ID cannot be negative")
                );
    }




    @Test
    void shouldReturn404_WhenIdIsVeryLarge() throws Exception {
        // Given
        Long largeId = Long.MAX_VALUE;
        Mockito.when(customerServiceInterface.getCustomerById(eq(largeId)))
                .thenThrow(new ResourceNotFoundException("Customer not found with id: " + largeId));

        // When & Then
        mockMvc.perform(get("/api/customers/{id}", largeId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }



    @Test
    void shouldReturn500_WhenServiceThrowsUnexpectedException() throws Exception {
        // Given
        Long validId = 123L;
        Mockito.when(customerServiceInterface.getCustomerById(eq(validId)))
                .thenThrow(new RuntimeException("Database connection failed"));

        // When & Then
        mockMvc.perform(get("/api/customers/{id}", validId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpectAll(
                        status().isInternalServerError(),
                        jsonPath("$.status").value(500),
                        jsonPath("$.error").value("Internal Server Error"),
                        jsonPath("$.message").value("An unexpected error occurred")
                );
    }



}