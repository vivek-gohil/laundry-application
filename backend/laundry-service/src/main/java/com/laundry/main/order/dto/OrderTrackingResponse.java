package com.laundry.main.order.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Public order tracking response")
public class OrderTrackingResponse {

    @Schema(description = "Unique order number", example = "ORD-20260616143000")
    private String orderNumber;

    @Schema(description = "Customer full name", example = "Rahul Sharma")
    private String customerName;

    @Schema(description = "Masked customer mobile number", example = "98******10")
    private String mobile;

    @Schema(description = "Current order status", example = "CREATED")
    private String status;

    @Schema(description = "Scheduled pickup date", example = "2026-06-20")
    private LocalDate pickupDate;

    @Schema(description = "Scheduled delivery date", example = "2026-06-22")
    private LocalDate deliveryDate;

    @Schema(description = "Total order amount", example = "250.00")
    private BigDecimal totalAmount;
}
