package com.laundry.main.customer.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Customer create or update request")
public class CustomerRequest {

    @NotBlank(message = "First name is required")
    @Size(max = 100, message = "First name must not exceed 100 characters")
    @Schema(example = "Rahul")
    private String firstName;

    @NotBlank(message = "Last name is required")
    @Size(max = 100, message = "Last name must not exceed 100 characters")
    @Schema(example = "Sharma")
    private String lastName;

    @NotBlank(message = "Mobile number is required")
    @Size(max = 20, message = "Mobile number must not exceed 20 characters")
    @Schema(example = "9876543210")
    private String mobile;

    @NotBlank(message = "Email is required")
    @Email(message = "Email must be valid")
    @Size(max = 255, message = "Email must not exceed 255 characters")
    @Schema(example = "rahul.sharma@example.com")
    private String email;

    @NotBlank(message = "Flat number is required")
    @Size(max = 50, message = "Flat number must not exceed 50 characters")
    @Schema(example = "A-204")
    private String flatNumber;

    @NotBlank(message = "Building name is required")
    @Size(max = 150, message = "Building name must not exceed 150 characters")
    @Schema(example = "Green Heights")
    private String buildingName;

    @NotBlank(message = "Address line 1 is required")
    @Size(max = 255, message = "Address line 1 must not exceed 255 characters")
    @Schema(example = "12 MG Road")
    private String addressLine1;

    @Size(max = 255, message = "Address line 2 must not exceed 255 characters")
    @Schema(example = "Near Central Mall")
    private String addressLine2;

    @NotBlank(message = "City is required")
    @Size(max = 100, message = "City must not exceed 100 characters")
    @Schema(example = "Bengaluru")
    private String city;

    @NotBlank(message = "Pincode is required")
    @Size(max = 10, message = "Pincode must not exceed 10 characters")
    @Schema(example = "560001")
    private String pincode;

    @Size(max = 255, message = "Landmark must not exceed 255 characters")
    @Schema(example = "Opposite Metro Station")
    private String landmark;

    @Builder.Default
    @Schema(example = "true")
    private Boolean active = true;
}
