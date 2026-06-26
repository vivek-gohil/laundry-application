package com.laundry.main.servicecatalog.controller;

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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.laundry.main.exception.GlobalExceptionHandler;
import com.laundry.main.exception.ResourceNotFoundException;
import com.laundry.main.servicecatalog.dto.ServiceMasterRequest;
import com.laundry.main.servicecatalog.dto.ServiceMasterResponse;
import com.laundry.main.servicecatalog.service.ServiceMasterService;
import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

@ExtendWith(MockitoExtension.class)
class ServiceMasterControllerTest {

  private final ObjectMapper objectMapper = new ObjectMapper();

  private MockMvc mockMvc;

  @Mock private ServiceMasterService serviceMasterService;

  @InjectMocks private ServiceMasterController serviceMasterController;

  @BeforeEach
  void setUp() {
    LocalValidatorFactoryBean validator = new LocalValidatorFactoryBean();
    validator.afterPropertiesSet();

    mockMvc =
        MockMvcBuilders.standaloneSetup(serviceMasterController)
            .setControllerAdvice(new GlobalExceptionHandler())
            .setValidator(validator)
            .build();
  }

  @Test
  void createServiceReturnsCreatedService() throws Exception {
    ServiceMasterRequest request = serviceMasterRequest();
    ServiceMasterResponse response = serviceMasterResponse();

    when(serviceMasterService.createService(any(ServiceMasterRequest.class))).thenReturn(response);

    mockMvc
        .perform(
            post("/api/services")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.success").value(true))
        .andExpect(jsonPath("$.message").value("Service created successfully"))
        .andExpect(jsonPath("$.data.serviceId").value(1))
        .andExpect(jsonPath("$.data.serviceName").value("Wash"))
        .andExpect(jsonPath("$.data.price").value(25.00));
  }

  @Test
  void getServiceByIdReturnsService() throws Exception {
    when(serviceMasterService.getServiceById(1L)).thenReturn(serviceMasterResponse());

    mockMvc
        .perform(get("/api/services/{serviceId}", 1L))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.success").value(true))
        .andExpect(jsonPath("$.message").value("Service fetched successfully"))
        .andExpect(jsonPath("$.data.serviceId").value(1));
  }

  @Test
  void getAllServicesReturnsServices() throws Exception {
    when(serviceMasterService.getAllServices()).thenReturn(List.of(serviceMasterResponse()));

    mockMvc
        .perform(get("/api/services"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.success").value(true))
        .andExpect(jsonPath("$.message").value("Services fetched successfully"))
        .andExpect(jsonPath("$.data", hasSize(1)));
  }

  @Test
  void updateServiceReturnsUpdatedService() throws Exception {
    ServiceMasterRequest request = serviceMasterRequest();
    ServiceMasterResponse response = serviceMasterResponse();

    when(serviceMasterService.updateService(eq(1L), any(ServiceMasterRequest.class)))
        .thenReturn(response);

    mockMvc
        .perform(
            put("/api/services/{serviceId}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.success").value(true))
        .andExpect(jsonPath("$.message").value("Service updated successfully"))
        .andExpect(jsonPath("$.data.serviceId").value(1));
  }

  @Test
  void deleteServiceReturnsSuccessResponse() throws Exception {
    doNothing().when(serviceMasterService).deleteService(1L);

    mockMvc
        .perform(delete("/api/services/{serviceId}", 1L))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.success").value(true))
        .andExpect(jsonPath("$.message").value("Service deleted successfully"));
  }

  @Test
  void getServiceByIdReturnsNotFoundForMissingService() throws Exception {
    when(serviceMasterService.getServiceById(99L))
        .thenThrow(new ResourceNotFoundException("Service not found"));

    mockMvc
        .perform(get("/api/services/{serviceId}", 99L))
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("$.success").value(false))
        .andExpect(jsonPath("$.message").value("Service not found"))
        .andExpect(jsonPath("$.data.message").value("Service not found"));
  }

  @Test
  void createServiceReturnsBadRequestForInvalidRequest() throws Exception {
    ServiceMasterRequest request = new ServiceMasterRequest();
    request.setServiceName("");
    request.setPrice(new BigDecimal("0"));

    mockMvc
        .perform(
            post("/api/services")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.success").value(false))
        .andExpect(jsonPath("$.message").value("Validation failed"));
  }

  private ServiceMasterRequest serviceMasterRequest() {
    ServiceMasterRequest request = new ServiceMasterRequest();
    request.setServiceName("Wash");
    request.setPrice(new BigDecimal("25.00"));
    request.setActive(true);
    return request;
  }

  private ServiceMasterResponse serviceMasterResponse() {
    return ServiceMasterResponse.builder()
        .serviceId(1L)
        .serviceName("Wash")
        .price(new BigDecimal("25.00"))
        .active(true)
        .build();
  }
}
