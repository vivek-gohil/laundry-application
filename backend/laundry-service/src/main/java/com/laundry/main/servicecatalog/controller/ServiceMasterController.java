package com.laundry.main.servicecatalog.controller;

import com.laundry.main.common.ApiResponse;
import com.laundry.main.servicecatalog.dto.ServiceMasterRequest;
import com.laundry.main.servicecatalog.dto.ServiceMasterResponse;
import com.laundry.main.servicecatalog.service.ServiceMasterService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
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

import java.util.List;

@RestController
@RequestMapping("/api/services")
@RequiredArgsConstructor
@Tag(name = "Service Master", description = "Service Master CRUD APIs")
public class ServiceMasterController {

    private final ServiceMasterService serviceMasterService;

    @PostMapping
    @Operation(summary = "Create service", description = "Creates a new service")
    public ResponseEntity<ApiResponse<ServiceMasterResponse>> createService(
            @Parameter(description = "Service create request") @Valid @RequestBody ServiceMasterRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Service created successfully", serviceMasterService.createService(request)));
    }

    @GetMapping("/{serviceId}")
    @Operation(summary = "Get service by id", description = "Returns a service by service id")
    public ResponseEntity<ApiResponse<ServiceMasterResponse>> getServiceById(
            @Parameter(description = "Service id") @PathVariable Long serviceId) {
        return ResponseEntity.ok(ApiResponse.success(
                "Service fetched successfully",
                serviceMasterService.getServiceById(serviceId)));
    }

    @GetMapping
    @Operation(summary = "Get all services", description = "Returns all services")
    public ResponseEntity<ApiResponse<List<ServiceMasterResponse>>> getAllServices() {
        return ResponseEntity.ok(ApiResponse.success(
                "Services fetched successfully",
                serviceMasterService.getAllServices()));
    }

    @PutMapping("/{serviceId}")
    @Operation(summary = "Update service", description = "Updates an existing service")
    public ResponseEntity<ApiResponse<ServiceMasterResponse>> updateService(
            @Parameter(description = "Service id") @PathVariable Long serviceId,
            @Parameter(description = "Service update request") @Valid @RequestBody ServiceMasterRequest request) {
        return ResponseEntity.ok(ApiResponse.success(
                "Service updated successfully",
                serviceMasterService.updateService(serviceId, request)));
    }

    @DeleteMapping("/{serviceId}")
    @Operation(summary = "Delete service", description = "Deletes an existing service")
    public ResponseEntity<ApiResponse<Void>> deleteService(@PathVariable Long serviceId) {
        serviceMasterService.deleteService(serviceId);
        return ResponseEntity.ok(ApiResponse.success("Service deleted successfully", null));
    }
}
