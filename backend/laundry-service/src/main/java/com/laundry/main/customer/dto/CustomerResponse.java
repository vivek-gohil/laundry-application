package com.laundry.main.customer.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Customer response")
public class CustomerResponse {

    @Schema(example = "1")
    private Long customerId;

    @Schema(example = "Rahul")
    private String firstName;

    @Schema(example = "Sharma")
    private String lastName;

    @Schema(example = "9876543210")
    private String mobile;

    @Schema(example = "rahul.sharma@example.com")
    private String email;

    @Schema(example = "A-204")
    private String flatNumber;

    @Schema(example = "Green Heights")
    private String buildingName;

    @Schema(example = "12 MG Road")
    private String addressLine1;

    @Schema(example = "Near Central Mall")
    private String addressLine2;

    @Schema(example = "Bengaluru")
    private String city;

    @Schema(example = "560001")
    private String pincode;

    @Schema(example = "Opposite Metro Station")
    private String landmark;

    @Schema(example = "true")
    private Boolean active;
}
