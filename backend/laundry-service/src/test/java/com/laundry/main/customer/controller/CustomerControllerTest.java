package com.laundry.main.customer.controller;

import com.laundry.main.customer.dto.CustomerRequest;
import com.laundry.main.customer.dto.CustomerResponse;
import com.laundry.main.customer.service.CustomerService;
import com.laundry.main.exception.GlobalExceptionHandler;
import com.laundry.main.exception.ResourceNotFoundException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class CustomerControllerTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    private MockMvc mockMvc;

    @Mock
    private CustomerService customerService;

    @InjectMocks
    private CustomerController customerController;

    @BeforeEach
    void setUp() {
        LocalValidatorFactoryBean validator = new LocalValidatorFactoryBean();
        validator.afterPropertiesSet();

        mockMvc = MockMvcBuilders.standaloneSetup(customerController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .setValidator(validator)
                .build();
    }

    @Test
    void createCustomerReturnsCreatedCustomer() throws Exception {
        CustomerRequest request = customerRequest();
        CustomerResponse response = customerResponse();

        when(customerService.createCustomer(any(CustomerRequest.class))).thenReturn(response);

        mockMvc.perform(post("/api/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Customer created successfully"))
                .andExpect(jsonPath("$.data.customerId").value(1))
                .andExpect(jsonPath("$.data.flatNumber").value("A-204"))
                .andExpect(jsonPath("$.data.buildingName").value("Green Heights"))
                .andExpect(jsonPath("$.data.email").value("rahul.sharma@example.com"));
    }

    @Test
    void getCustomerByIdReturnsCustomer() throws Exception {
        when(customerService.getCustomerById(1L)).thenReturn(customerResponse());

        mockMvc.perform(get("/api/customers/{customerId}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Customer fetched successfully"))
                .andExpect(jsonPath("$.data.customerId").value(1));
    }

    @Test
    void getAllCustomersReturnsCustomers() throws Exception {
        when(customerService.getAllCustomers()).thenReturn(List.of(customerResponse()));

        mockMvc.perform(get("/api/customers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Customers fetched successfully"))
                .andExpect(jsonPath("$.data", hasSize(1)));
    }

    @Test
    void updateCustomerReturnsUpdatedCustomer() throws Exception {
        CustomerRequest request = customerRequest();
        CustomerResponse response = customerResponse();

        when(customerService.updateCustomer(eq(1L), any(CustomerRequest.class))).thenReturn(response);

        mockMvc.perform(put("/api/customers/{customerId}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Customer updated successfully"))
                .andExpect(jsonPath("$.data.customerId").value(1));
    }

    @Test
    void deleteCustomerReturnsSuccessResponse() throws Exception {
        doNothing().when(customerService).deleteCustomer(1L);

        mockMvc.perform(delete("/api/customers/{customerId}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Customer deleted successfully"));
    }

    @Test
    void getCustomerByIdReturnsNotFoundForMissingCustomer() throws Exception {
        when(customerService.getCustomerById(99L)).thenThrow(new ResourceNotFoundException("Customer not found"));

        mockMvc.perform(get("/api/customers/{customerId}", 99L))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message").value("Customer not found"))
                .andExpect(jsonPath("$.data.message").value("Customer not found"));
    }

    @Test
    void createCustomerReturnsBadRequestForInvalidRequest() throws Exception {
        CustomerRequest request = customerRequest();
        request.setEmail("invalid-email");

        mockMvc.perform(post("/api/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message").value("Validation failed"))
                .andExpect(jsonPath("$.data.validationErrors.email").value("Email must be valid"));
    }

    private CustomerRequest customerRequest() {
        return CustomerRequest.builder()
                .firstName("Rahul")
                .lastName("Sharma")
                .mobile("9876543210")
                .email("rahul.sharma@example.com")
                .flatNumber("A-204")
                .buildingName("Green Heights")
                .addressLine1("12 MG Road")
                .addressLine2("Near Central Mall")
                .city("Bengaluru")
                .pincode("560001")
                .landmark("Opposite Metro Station")
                .active(true)
                .build();
    }

    private CustomerResponse customerResponse() {
        return CustomerResponse.builder()
                .customerId(1L)
                .firstName("Rahul")
                .lastName("Sharma")
                .mobile("9876543210")
                .email("rahul.sharma@example.com")
                .flatNumber("A-204")
                .buildingName("Green Heights")
                .addressLine1("12 MG Road")
                .addressLine2("Near Central Mall")
                .city("Bengaluru")
                .pincode("560001")
                .landmark("Opposite Metro Station")
                .active(true)
                .build();
    }
}
