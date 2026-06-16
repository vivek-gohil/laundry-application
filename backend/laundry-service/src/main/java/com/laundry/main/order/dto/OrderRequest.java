package com.laundry.main.order.dto;

import java.time.LocalDate;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(description = "Order create request")
public class OrderRequest {

    @Schema(description = "Customer id for the order", example = "1")
    @NotNull(message = "Customer id is required")
    private Long customerId;

    @Schema(description = "Pickup date for the order", example = "2026-06-20")
    private LocalDate pickupDate;

    @Schema(description = "Delivery date for the order", example = "2026-06-22")
    private LocalDate deliveryDate;

    @Schema(description = "Order items to be included in the order")
    @NotNull(message = "Order items are required")
    @Valid
    private List<OrderItemRequest> items;
}
