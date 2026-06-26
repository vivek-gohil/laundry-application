package com.laundry.main.customer.controller;

import com.laundry.main.common.ApiResponse;
import com.laundry.main.customer.dto.CustomerRequest;
import com.laundry.main.customer.dto.CustomerResponse;
import com.laundry.main.customer.service.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
@Tag(name = "Customer", description = "Customer CRUD APIs")
public class CustomerController {

  private final CustomerService customerService;

  @PostMapping
  @Operation(summary = "Create customer", description = "Creates a new customer")
  public ResponseEntity<ApiResponse<CustomerResponse>> createCustomer(
      @Valid @RequestBody CustomerRequest request) {
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(
            ApiResponse.success(
                "Customer created successfully", customerService.createCustomer(request)));
  }

  @GetMapping("/{customerId}")
  @Operation(summary = "Get customer by id", description = "Returns a customer by customer id")
  public ResponseEntity<ApiResponse<CustomerResponse>> getCustomerById(
      @PathVariable Long customerId) {
    return ResponseEntity.ok(
        ApiResponse.success(
            "Customer fetched successfully", customerService.getCustomerById(customerId)));
  }

  @GetMapping
  @Operation(summary = "Get all customers", description = "Returns all customers")
  public ResponseEntity<ApiResponse<List<CustomerResponse>>> getAllCustomers() {
    return ResponseEntity.ok(
        ApiResponse.success("Customers fetched successfully", customerService.getAllCustomers()));
  }

  @PutMapping("/{customerId}")
  @Operation(summary = "Update customer", description = "Updates an existing customer")
  public ResponseEntity<ApiResponse<CustomerResponse>> updateCustomer(
      @PathVariable Long customerId, @Valid @RequestBody CustomerRequest request) {
    return ResponseEntity.ok(
        ApiResponse.success(
            "Customer updated successfully", customerService.updateCustomer(customerId, request)));
  }

  @DeleteMapping("/{customerId}")
  @Operation(summary = "Delete customer", description = "Deletes an existing customer")
  public ResponseEntity<ApiResponse<Void>> deleteCustomer(@PathVariable Long customerId) {
    customerService.deleteCustomer(customerId);
    return ResponseEntity.ok(ApiResponse.success("Customer deleted successfully", null));
  }
}
